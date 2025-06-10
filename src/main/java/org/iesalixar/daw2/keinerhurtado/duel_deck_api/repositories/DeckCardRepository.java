package org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.DeckCard;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.DeckCardId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeckCardRepository extends JpaRepository<DeckCard, DeckCardId> {
    List<DeckCard> findByDeck_Id(Long deckId);
    List<DeckCard> findByCard_Id(Long cardId);
}
