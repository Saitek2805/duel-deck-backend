package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.CardType;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.CardTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card-types")
public class CardTypeController {

    @Autowired
    private CardTypeRepository cardTypeRepository;

    @GetMapping
    public ResponseEntity<List<CardType>> getAll() {
        return ResponseEntity.ok(cardTypeRepository.findAll());
    }
}
