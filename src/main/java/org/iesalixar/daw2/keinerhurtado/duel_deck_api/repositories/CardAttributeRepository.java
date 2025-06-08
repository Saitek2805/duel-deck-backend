package org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.CardAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardAttributeRepository extends JpaRepository<CardAttribute, Long> {
    Optional<CardAttribute> findByName(String name);
}
