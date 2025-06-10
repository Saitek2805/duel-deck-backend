package org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.DeckComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeckCommentRepository extends JpaRepository<DeckComment, Long> {
    List<DeckComment> findByDeck_Id(Long deckId);
    List<DeckComment> findByUser_Id(Long userId);
}
