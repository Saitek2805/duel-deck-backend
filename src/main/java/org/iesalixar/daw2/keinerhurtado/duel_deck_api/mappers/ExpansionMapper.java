package org.iesalixar.daw2.keinerhurtado.duel_deck_api.mappers;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.ExpansionCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.ExpansionDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Expansion;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre entidades y DTOs de expansiones.
 */
@Component
public class ExpansionMapper {

    /**
     * Convierte una entidad Expansion en un DTO ExpansionDTO.
     *
     * @param expansion La entidad Expansion a convertir.
     * @return Un objeto ExpansionDTO.
     */
    public ExpansionDTO toDTO(Expansion expansion) {
        return new ExpansionDTO(
                expansion.getId(),
                expansion.getCode(),
                expansion.getName(),
                expansion.getReleaseDate(),
                expansion.getDescription(),
                expansion.getImage()
        );
    }

    /**
     * Convierte un DTO ExpansionDTO en una entidad Expansion.
     *
     * @param dto DTO con la información de la expansión.
     * @return Una entidad Expansion.
     */
    public Expansion toEntity(ExpansionDTO dto) {
        Expansion expansion = new Expansion();
        expansion.setId(dto.getId());
        expansion.setCode(dto.getCode());
        expansion.setName(dto.getName());
        expansion.setReleaseDate(dto.getReleaseDate());
        expansion.setDescription(dto.getDescription());
        expansion.setImage(dto.getImage());
        return expansion;
    }

    /**
     * Convierte un DTO ExpansionCreateDTO en una entidad Expansion.
     *
     * @param createDTO DTO con la información para crear una nueva expansión.
     * @return Una nueva entidad Expansion.
     */
    public Expansion toEntity(ExpansionCreateDTO createDTO) {
        Expansion expansion = new Expansion();
        expansion.setCode(createDTO.getCode());
        expansion.setName(createDTO.getName());
        expansion.setReleaseDate(createDTO.getReleaseDate());
        expansion.setDescription(createDTO.getDescription());
        expansion.setImage(createDTO.getImage());
        return expansion;
    }
}
