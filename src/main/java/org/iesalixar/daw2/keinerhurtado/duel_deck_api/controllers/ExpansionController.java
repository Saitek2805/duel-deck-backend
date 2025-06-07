package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.ExpansionCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.ExpansionDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.services.ExpansionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Controlador REST para gestionar las expansiones.
 * Este controlador maneja las solicitudes HTTP relacionadas con las expansiones,
 * como crear, actualizar, eliminar y obtener expansiones.
 */
@RestController
@RequestMapping("/api/expansions")  // El controlador maneja las rutas de "/api/expansions"
public class ExpansionController {

    private static final Logger logger = LoggerFactory.getLogger(ExpansionController.class);  // Logger para registrar los eventos
    @Autowired
    private ExpansionService expansionService;  // Servicio que contiene la lógica de negocio de las expansiones

    /**
     * Endpoint para obtener todas las expansiones.
     * @return Una lista de todas las expansiones en formato DTO.
     */
    @Operation(summary = "Obtener todas las expansiones", description = "Devuelve una lista con todas las expansiones disponibles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de expansiones recuperada exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ExpansionDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<ExpansionDTO>> getAllExpansions(
            @PageableDefault (size = 10,sort = "name") Pageable pageable
            ) {
        logger.info("Solicitando la lista de todas las expansiones con paginación: página {}, tamaño {}",
                pageable.getPageNumber(),pageable.getPageSize());

        try {
            Page<ExpansionDTO> expansions = expansionService.getAllExpansions(pageable);  // Obtiene todas las expansiones

            logger.info("Se han cargado {} expansiones.", expansions.getTotalElements());
            return ResponseEntity.ok(expansions);  // Devuelve las expansiones con un código de estado 200
        } catch (Exception e) {
            logger.error("Error al listar las expansiones: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Devuelve un error 500 si ocurre una excepción
        }
    }

    /**
     * Endpoint para obtener una expansión por su ID.
     * @param id El ID de la expansión que se desea obtener.
     * @return La expansión encontrada o un error si no se encuentra.
     */
    @Operation(summary = "Obtener una expansión por ID", description = "Devuelve los detalles de una expansión específica según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expansión encontrada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExpansionDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna expansión con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getExpansionById(@PathVariable Long id) {
        logger.info("Buscando expansión con ID : {}", id);

        try {
            Optional<ExpansionDTO> expansionDTO = Optional.ofNullable(expansionService.getExpansionById(id));  // Busca la expansión por ID

            if (expansionDTO.isPresent()) {
                return ResponseEntity.ok(expansionDTO.get());  // Si se encuentra, se devuelve con un código de estado 200
            } else {
                logger.warn("No se encontró expansión con ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Devuelve un error 404 si no se encuentra
            }
        } catch (Exception e) {
            logger.error("Error al buscar la expansión con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Devuelve un error 500 si ocurre una excepción
        }
    }

    /**
     * Endpoint para actualizar una expansión existente.
     * @param id El ID de la expansión que se desea actualizar.
     * @param expansionCreateDTO El DTO con los nuevos datos de la expansión.
     * @param locale El locale para los mensajes de error.
     * @return La expansión actualizada o un error si algo falla.
     */
    @Operation(summary = "Actualizar una expansión", description = "Actualiza los detalles de una expansión existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expansión actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExpansionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida, los datos de entrada son incorrectos"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna expansión con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpansion(@PathVariable Long id, @Valid @RequestBody ExpansionCreateDTO expansionCreateDTO,
                                             Locale locale) {
        logger.info("Actualizando expansión con ID {}", id);
        try {
            ExpansionDTO updatedExpansion = expansionService.updateExpansion(id, expansionCreateDTO, locale);  // Llama al servicio para actualizar la expansión

            logger.info("Expansión con ID {} actualizada con éxito.", id);
            return ResponseEntity.ok(updatedExpansion);  // Devuelve la expansión actualizada con un código de estado 200
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar la expansión con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  // Devuelve un error 400 si ocurre un error en la entrada
        } catch (Exception e) {
            logger.error("Error al actualizar la expansión con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la expansión");  // Error 500 si hay problemas internos
        }
    }

    /**
     * Endpoint para crear una nueva expansión.
     * @param expansionCreateDTO Los datos de la expansión a crear.
     * @param locale El locale para los mensajes de error.
     * @return La expansión creada o un error si algo falla.
     */
    @Operation(summary = "Crear una nueva expansión", description = "Crea una nueva expansión en el sistema y la devuelve con su ID generado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Expansión creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExpansionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida, los datos de entrada son incorrectos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createExpansion(@Valid @RequestBody ExpansionCreateDTO expansionCreateDTO, Locale locale) {
        logger.info("Insertando nueva expansión con código {}", expansionCreateDTO.getCode());
        try {
            ExpansionDTO createdExpansion = expansionService.createExpansion(expansionCreateDTO, locale);  // Llama al servicio para crear la expansión
            logger.info("Expansión creada exitosamente con ID {}", createdExpansion.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExpansion);  // Devuelve la expansión creada con un código de estado 201
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear la expansión: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  // Error 400 si la entrada es incorrecta
        } catch (Exception e) {
            logger.error("Error al crear la expansión: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la expansión");  // Error 500 si hay problemas internos
        }
    }

    /**
     * Endpoint para eliminar una expansión por su ID.
     * @param id El ID de la expansión que se desea eliminar.
     * @return Un mensaje indicando si la eliminación fue exitosa o un error si no se encuentra.
     */
    @Operation(summary = "Eliminar una expansión", description = "Elimina una expansión existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expansión eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna expansión con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpansion(@PathVariable Long id) {
        logger.info("Eliminando expansión con ID {}", id);
        try {
            expansionService.deleteExpansion(id);  // Llama al servicio para eliminar la expansión
            logger.info("Expansión con ID {} eliminada con éxito.", id);
            return ResponseEntity.ok("Expansión eliminada con éxito");  // Devuelve un mensaje de éxito con un código de estado 200
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar la expansión con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  // Error 404 si no se encuentra la expansión
        } catch (Exception e) {
            logger.error("Error al eliminar la expansión con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la expansión");  // Error 500 si hay problemas internos
        }
    }
}
