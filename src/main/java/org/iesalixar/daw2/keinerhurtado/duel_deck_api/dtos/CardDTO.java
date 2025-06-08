package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    private Long id;
    private String code;
    private String name;

    private String type;
    private String typing;
    private String attribute;

    private Integer level;
    private Integer attack;
    private Integer defense;

    private String rarity;
    private String image;
    private String description;

    private ExpansionDTO expansion;
}
