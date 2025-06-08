package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CardCreateDTO {

    @NotEmpty(message = "{msg.card.code.notEmpty}")
    @Size(max = 10, message = "{msg.card.code.size}")
    private String code;

    @NotEmpty(message = "{msg.card.name.notEmpty}")
    @Size(max = 100, message = "{msg.card.name.size}")
    private String name;

    @Size(max = 50, message = "{msg.card.cardType.size}")
    private String type;

    @Size(max = 50, message = "{msg.card.typing.size}")
    private String typing;

    @Size(max = 20, message = "{msg.card.attribute.size}")
    private String attribute;

    private Integer level;
    private Integer attack;
    private Integer defense;

    @Size(max = 50, message = "{msg.card.rarity.size}")
    private String rarity;

    @Size(max = 255, message = "{msg.card.image.size}")
    private String image;

    @Size(max = 255, message = "{msg.card.description.size}")
    private String description;

    private Long expansion; // ID de la expansión
}
