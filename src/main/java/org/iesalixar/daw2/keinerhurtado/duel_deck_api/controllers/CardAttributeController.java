package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.CardAttribute;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.CardAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card-attributes")
public class CardAttributeController {

    @Autowired
    private CardAttributeRepository cardAttributeRepository;

    @GetMapping
    public ResponseEntity<List<CardAttribute>> getAll() {
        return ResponseEntity.ok(cardAttributeRepository.findAll());
    }
}
