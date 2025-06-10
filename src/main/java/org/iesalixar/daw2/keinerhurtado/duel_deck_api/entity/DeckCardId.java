package org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeckCardId implements Serializable {
    private Long deck;
    private Long card;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeckCardId that)) return false;
        return Objects.equals(deck, that.deck) && Objects.equals(card, that.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deck, card);
    }
}
