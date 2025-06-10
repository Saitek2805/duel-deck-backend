package org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "deck_cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(DeckCardId.class)
public class DeckCard {

    @Id
    @ManyToOne
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    @Id
    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(nullable = false)
    private int quantity;
}
