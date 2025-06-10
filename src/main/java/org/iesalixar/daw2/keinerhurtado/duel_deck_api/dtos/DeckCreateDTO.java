package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeckCreateDTO {
    private String name;
    private String description;
    private String image;
    private Long userId; // el usuario al que pertenece el mazo
}
