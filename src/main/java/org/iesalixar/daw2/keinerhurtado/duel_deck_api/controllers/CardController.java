package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.CardCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.CardDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

/**
 * Controlador REST para gestionar las cartas.
 * Este controlador maneja las solicitudes HTTP relacionadas con las cartas,
 * como crear, actualizar, eliminar y obtener cartas.
 */
@RestController
@RequestMapping("/api/cards")
//@CrossOrigin(origins = "http://localhost:4200")// El controlador maneja las rutas de "/api/cards"
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);  // Logger para registrar eventos
    @Autowired
    private CardService cardService;  // Servicio que contiene la lógica de negocio de las cartas

    /**
     * Endpoint para obtener todas las cartas.
     * @return Una lista de todas las cartas en formato DTO.
     */
    @Operation(summary = "Obtener todas las cartas", description = "Devuelve una lista con todas las cartas y sus expansiones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cartas recuperada exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CardDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<CardDTO>> getAllCards(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        logger.info("Solicitud GET /api/cards con paginación");
        try {
            Page<CardDTO> page = cardService.getAllCards(pageable);
            return ResponseEntity.ok(page);
        } catch (Exception e) {
            logger.error("Error al listar cartas paginadas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Page.empty());
        }
    }


    /**
     * Endpoint para crear una nueva carta.
     * @param cardCreateDTO Los datos de la carta a crear.
     * @param locale El locale para los mensajes de error.
     * @return La carta creada o un error si algo falla.
     */
    @Operation(summary = "Crear una nueva carta", description = "Crea una nueva carta en el sistema y la devuelve con su ID generado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carta creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida, los datos de entrada son incorrectos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createCard(@Valid @RequestBody CardCreateDTO cardCreateDTO, Locale locale) {
        logger.info("Insertando nueva carta con código {}", cardCreateDTO.getCode());
        try {
            CardDTO createdCard = cardService.createCard(cardCreateDTO, locale);  // Llama al servicio para crear la carta
            logger.info("Carta creada exitosamente con ID {}", createdCard.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCard);  // Devuelve la carta creada con un código de estado 201
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear la carta: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  // Error 400 si la entrada es incorrecta
        } catch (Exception e) {
            logger.error("Error al crear la carta: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la carta");  // Error 500 si ocurre un problema interno
        }
    }

    /**
     * Endpoint para obtener una carta por su ID.
     * @param id El ID de la carta que se desea obtener.
     * @return La carta encontrada o un error si no se encuentra.
     */
    @Operation(summary = "Obtener una carta por ID", description = "Devuelve los detalles de una carta específica según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carta encontrada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna carta con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getCardById(@PathVariable Long id) {
        logger.info("Buscando carta con ID: {}", id);
        try {
            CardDTO cardDTO = cardService.getCardById(id);  // Llama al servicio para obtener la carta por ID
            logger.info("Carta con ID {} encontrada.", id);
            return ResponseEntity.ok(cardDTO);  // Devuelve la carta encontrada con un código de estado 200
        } catch (IllegalArgumentException e) {
            logger.warn("No se encontró ninguna carta con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  // Error 404 si no se encuentra la carta
        } catch (Exception e) {
            logger.error("Error al obtener la carta con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Error 500 si ocurre un problema interno
        }
    }

    /**
     * Endpoint para actualizar una carta existente.
     * @param id El ID de la carta que se desea actualizar.
     * @param cardCreateDTO El DTO con los nuevos datos de la carta.
     * @param locale El locale para los mensajes de error.
     * @return La carta actualizada o un error si algo falla.
     */
    @Operation(summary = "Actualizar una carta", description = "Actualiza los detalles de una carta existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carta actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida, los datos de entrada son incorrectos"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna carta con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCard(@PathVariable Long id, @Valid @RequestBody CardCreateDTO cardCreateDTO, Locale locale) {
        logger.info("Actualizando carta con ID {}", id);
        try {
            CardDTO updatedCard = cardService.updateCard(id, cardCreateDTO, locale);  // Llama al servicio para actualizar la carta
            logger.info("Carta con ID {} actualizada exitosamente.", id);
            return ResponseEntity.ok(updatedCard);  // Devuelve la carta actualizada con un código de estado 200
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar la carta con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  // Error 400 si la entrada es incorrecta
        } catch (Exception e) {
            logger.error("Error al actualizar la carta con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la carta");  // Error 500 si ocurre un problema interno
        }
    }

    /**
     * Endpoint para eliminar una carta por su ID.
     * @param id El ID de la carta que se desea eliminar.
     * @return Un mensaje indicando si la eliminación fue exitosa o un error si no se encuentra.
     */
    @Operation(summary = "Eliminar una carta", description = "Elimina una carta existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna carta con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id) {
        logger.info("Eliminando carta con ID {}", id);
        try {
            cardService.deleteCard(id);  // Llama al servicio para eliminar la carta
            logger.info("Carta con ID {} eliminada exitosamente.", id);
            return ResponseEntity.ok("Carta eliminada con éxito.");  // Devuelve un mensaje de éxito con un código de estado 200
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar la carta con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  // Error 404 si no se encuentra la carta
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar la carta con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la carta.");  // Error 500 si ocurre un problema interno
        }
    }

}
