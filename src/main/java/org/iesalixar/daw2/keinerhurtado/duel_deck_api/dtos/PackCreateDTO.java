package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter  // Lombok genera el método getter para todos los campos
@Setter  // Lombok genera el método setter para todos los campos
public class PackCreateDTO {
    private String image;
    private String description;
    private Long expansionId;
}