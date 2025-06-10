package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeckCommentDTO {
    private Long id;
    private Long deckId;
    private String deckName;
    private Long userId;
    private String userName;
    private String content;
}
