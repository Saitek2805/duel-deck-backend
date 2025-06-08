package org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.CardRarity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRarityRepository extends JpaRepository<CardRarity, Long> {
    Optional<CardRarity> findByName(String name);
}
