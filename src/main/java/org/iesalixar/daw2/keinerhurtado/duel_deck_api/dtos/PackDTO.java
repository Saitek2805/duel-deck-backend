package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// DTO para visualización
public class PackDTO {
    private Long id;
    private String image;
    private String description;
    private Long expansionId;
    private String expansionName;
}