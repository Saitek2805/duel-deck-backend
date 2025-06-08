package org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.CardTyping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardTypingRepository extends JpaRepository<CardTyping, Long> {
    Optional<CardTyping> findByName(String name);
}
