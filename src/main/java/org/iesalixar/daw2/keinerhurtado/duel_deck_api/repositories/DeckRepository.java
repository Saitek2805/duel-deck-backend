package org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Long> {
    List<Deck> findByUser_Id(Long userId); // para listar los mazos de un usuario
}
