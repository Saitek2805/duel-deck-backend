package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.CardStatusDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.services.CardStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card-status")
public class CardStatusController {

    @Autowired
    private CardStatusService cardStatusService;

    // Cambia el estado de una carta
    @PutMapping("/{cardId}")
    public ResponseEntity<CardStatusDTO> changeStatus(
            @PathVariable Long cardId,
            @RequestParam String status // Forbidden, Limited, Semi-Limited
    ) {
        try {
            CardStatusDTO updated = cardStatusService.changeStatus(cardId, status);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{cardId}")
    public ResponseEntity<CardStatusDTO> getStatus(@PathVariable Long cardId) {
        try {
            CardStatusDTO dto = cardStatusService.getStatus(cardId);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<CardStatusDTO>> getAllStatuses() {
        List<CardStatusDTO> list = cardStatusService.getAllStatuses();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long cardId) {
        try {
            cardStatusService.deleteStatus(cardId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
