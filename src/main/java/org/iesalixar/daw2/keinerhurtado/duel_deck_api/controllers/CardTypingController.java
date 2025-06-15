package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.CardTyping;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.CardTypingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card-typings")
public class CardTypingController {

    @Autowired
    private CardTypingRepository cardTypingRepository;

    @GetMapping
    public ResponseEntity<List<CardTyping>> getAll() {
        return ResponseEntity.ok(cardTypingRepository.findAll());
    }
}
