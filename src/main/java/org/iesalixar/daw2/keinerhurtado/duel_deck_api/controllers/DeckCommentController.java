package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.DeckCommentCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.DeckCommentDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.services.DeckCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deck-comments")
public class DeckCommentController {

    @Autowired
    private DeckCommentService deckCommentService;

    // Crear comentario
    @PostMapping
    public ResponseEntity<DeckCommentDTO> createComment(@RequestBody DeckCommentCreateDTO dto) {
        try {
            DeckCommentDTO created = deckCommentService.createComment(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Obtener todos los comentarios
    @GetMapping
    public ResponseEntity<List<DeckCommentDTO>> getAllComments() {
        return ResponseEntity.ok(deckCommentService.getAllComments());
    }

    // Listar comentarios por mazo
    @GetMapping("/deck/{deckId}")
    public ResponseEntity<List<DeckCommentDTO>> getCommentsByDeck(@PathVariable Long deckId) {
        return ResponseEntity.ok(deckCommentService.getCommentsByDeck(deckId));
    }

    // Listar comentarios por usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeckCommentDTO>> getCommentsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(deckCommentService.getCommentsByUser(userId));
    }

    // Obtener un comentario por id
    @GetMapping("/{id}")
    public ResponseEntity<DeckCommentDTO> getCommentById(@PathVariable Long id) {
        DeckCommentDTO dto = deckCommentService.getCommentById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    // Borrar un comentario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            deckCommentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
