package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeckCommentCreateDTO {
    private Long deckId;
    private Long userId;
    private String content;
}
