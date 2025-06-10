package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeckCardDTO {
    private Long deckId;
    private Long cardId;
    private String cardName;    // útil para el frontend
    private int quantity;
}
