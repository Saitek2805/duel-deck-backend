package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardStatusDTO {
    private Long cardId;
    private String cardName;  // opcional, útil para frontend
    private String status;    // "Forbidden", "Limited" o "Semi-Limited"
}