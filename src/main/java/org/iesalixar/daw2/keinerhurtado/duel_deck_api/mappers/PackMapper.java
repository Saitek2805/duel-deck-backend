package org.iesalixar.daw2.keinerhurtado.duel_deck_api.mappers;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.PackCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.PackDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Expansion;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Pack;

// Mapper
public class PackMapper {
    public static PackDTO toDTO(Pack pack) {
        PackDTO dto = new PackDTO();
        dto.setId(pack.getId());
        dto.setImage(pack.getImage());
        dto.setDescription(pack.getDescription());
        dto.setExpansionId(pack.getExpansion().getId());
        dto.setExpansionName(pack.getExpansion().getName());
        return dto;
    }

    public static Pack toEntity(PackCreateDTO dto, Expansion expansion) {
        Pack pack = new Pack();
        pack.setImage(dto.getImage());
        pack.setDescription(dto.getDescription());
        pack.setExpansion(expansion);
        return pack;
    }
}