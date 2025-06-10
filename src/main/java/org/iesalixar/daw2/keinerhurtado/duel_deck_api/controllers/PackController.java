package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.PackCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.PackDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.services.PackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packs")
public class PackController {

    private static final Logger logger = LoggerFactory.getLogger(PackController.class);

    @Autowired
    private PackService packService;

    // GET /api/packs
    @Operation(summary = "Obtener todos los packs", description = "Devuelve una lista de todos los packs")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping
    public ResponseEntity<List<PackDTO>> getAllPacks() {
        logger.info("GET /api/packs");
        return ResponseEntity.ok(packService.getAllPacks());
    }

    // GET /api/packs/{id}
    @Operation(summary = "Obtener pack por id", description = "Devuelve un pack por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PackDTO> getPackById(@PathVariable Long id) {
        logger.info("GET /api/packs/{}", id);
        PackDTO dto = packService.getPackById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    // POST /api/packs
    @Operation(summary = "Crear un nuevo pack", description = "Crea un nuevo pack asociado a una expansión")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creado"),
            @ApiResponse(responseCode = "400", description = "Error de validación")
    })
    @PostMapping
    public ResponseEntity<PackDTO> createPack(@RequestBody PackCreateDTO packCreateDTO) {
        logger.info("POST /api/packs");
        try {
            PackDTO created = packService.createPack(packCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            logger.error("Error al crear pack: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /api/packs/{id}
    @Operation(summary = "Editar un pack existente", description = "Modifica un pack existente por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "No encontrado"),
            @ApiResponse(responseCode = "400", description = "Error de validación")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PackDTO> updatePack(@PathVariable Long id, @RequestBody PackCreateDTO packCreateDTO) {
        logger.info("PUT /api/packs/{}", id);
        try {
            PackDTO updated = packService.updatePack(id, packCreateDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            logger.error("Error al actualizar pack: {}", e.getMessage());
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /api/packs/{id}
    @Operation(summary = "Eliminar un pack", description = "Elimina un pack por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePack(@PathVariable Long id) {
        logger.info("DELETE /api/packs/{}", id);
        try {
            packService.deletePack(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Error al eliminar pack: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
