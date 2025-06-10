package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.DeckCardCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.DeckCardDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.services.DeckCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deck-cards")
public class DeckCardController {

    @Autowired
    private DeckCardService deckCardService;

    // Listar todas las cartas de un mazo
    @GetMapping("/deck/{deckId}")
    public ResponseEntity<List<DeckCardDTO>> getCardsInDeck(@PathVariable Long deckId) {
        return ResponseEntity.ok(deckCardService.getCardsInDeck(deckId));
    }

    // Obtener una carta concreta de un mazo//esto seguramente lo borre mañna
    @GetMapping("/deck/{deckId}/card/{cardId}")
    public ResponseEntity<DeckCardDTO> getDeckCard(@PathVariable Long deckId, @PathVariable Long cardId) {
        DeckCardDTO dto = deckCardService.getDeckCard(deckId, cardId);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    // Añadir o actualizar una carta en un mazo
    @PostMapping
    public ResponseEntity<List<DeckCardDTO>> addOrUpdateDeckCards(@RequestBody List<DeckCardCreateDTO> dtos) {
        try {
            List<DeckCardDTO> result = deckCardService.addOrUpdateDeckCards(dtos);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    // Eliminar una carta de un mazo
    @DeleteMapping("/deck/{deckId}/card/{cardId}")
    public ResponseEntity<Void> deleteDeckCard(@PathVariable Long deckId, @PathVariable Long cardId) {
        try {
            deckCardService.deleteDeckCard(deckId, cardId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // Obtener todas las relaciones carta-mazo de todos los mazos
    @GetMapping
    public ResponseEntity<List<DeckCardDTO>> getAllDeckCards() {
        return ResponseEntity.ok(deckCardService.getAllDeckCards());
    }

}
