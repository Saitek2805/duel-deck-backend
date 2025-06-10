package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeckDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Long userId;
    private String userName; // útil para mostrar en frontend
}
