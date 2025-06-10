package org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardStatusRepository extends JpaRepository<CardStatus, Long> {
    // Puedes añadir métodos de consulta si lo necesitas, por ejemplo:
    // Optional<CardStatus> findByCard_Id(Long cardId);
}
