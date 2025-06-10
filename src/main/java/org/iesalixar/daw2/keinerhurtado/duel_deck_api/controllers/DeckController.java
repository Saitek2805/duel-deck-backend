package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.DeckCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.DeckDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.services.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/decks")
public class DeckController {

    @Autowired
    private DeckService deckService;

    // Listar todos los mazos
    @GetMapping
    public ResponseEntity<List<DeckDTO>> getAllDecks() {
        return ResponseEntity.ok(deckService.getAllDecks());
    }

    // Listar mazos de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeckDTO>> getDecksByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(deckService.getDecksByUserId(userId));
    }

    // Obtener un mazo por id
    @GetMapping("/{id}")
    public ResponseEntity<DeckDTO> getDeckById(@PathVariable Long id) {
        DeckDTO dto = deckService.getDeckById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    // Crear un mazo
    @PostMapping
    public ResponseEntity<DeckDTO> createDeck(@RequestBody DeckCreateDTO deckCreateDTO) {
        try {
            DeckDTO created = deckService.createDeck(deckCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Editar un mazo
    @PutMapping("/{id}")
    public ResponseEntity<DeckDTO> updateDeck(@PathVariable Long id, @RequestBody DeckCreateDTO deckCreateDTO) {
        try {
            DeckDTO updated = deckService.updateDeck(id, deckCreateDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Borrar un mazo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeck(@PathVariable Long id) {
        try {
            deckService.deleteDeck(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
