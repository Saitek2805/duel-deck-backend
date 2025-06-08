package org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Card;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.CardType;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.CardRarity;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Expansion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    /**
     * Verifica si ya existe una carta con el código especificado.
     */
    boolean existsByCode(String code);

    /**
     * Verifica si ya existe una carta con el código especificado, excluyendo una carta por ID.
     */
    @Query("SELECT COUNT(c) > 0 FROM Card c WHERE c.code = :code AND c.id != :id")
    boolean existsCardByCodeAndNotId(@Param("code") String code, @Param("id") Long id);

    /**
     * Devuelve todas las cartas almacenadas.
     */
    List<Card> findAll();

    /**
     * Encuentra cartas por tipo.
     */
    List<Card> findByType(CardType type);

    /**
     * Encuentra cartas por rareza.
     */
    List<Card> findByRarity(CardRarity rarity);

    /**
     * Encuentra cartas por expansión.
     */
    List<Card> findByExpansion(Expansion expansion);

    /**
     * Encuentra cartas por fragmento del nombre (búsqueda parcial, case-insensitive).
     */
    List<Card> findByNameContainingIgnoreCase(String name);

    /**
     * Encuentra cartas por código exacto.
     */
    Card findByCode(String code);
}
