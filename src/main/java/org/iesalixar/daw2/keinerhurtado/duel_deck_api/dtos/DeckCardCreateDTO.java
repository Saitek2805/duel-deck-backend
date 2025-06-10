package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeckCardCreateDTO {
    private Long deckId;
    private Long cardId;
    private int quantity;
}
