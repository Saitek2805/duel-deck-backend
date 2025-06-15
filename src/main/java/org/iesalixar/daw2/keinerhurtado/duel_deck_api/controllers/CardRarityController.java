package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.CardRarity;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.CardRarityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card-rarities")
public class CardRarityController {

    @Autowired
    private CardRarityRepository cardRarityRepository;

    @GetMapping
    public ResponseEntity<List<CardRarity>> getAll() {
        return ResponseEntity.ok(cardRarityRepository.findAll());
    }
}
