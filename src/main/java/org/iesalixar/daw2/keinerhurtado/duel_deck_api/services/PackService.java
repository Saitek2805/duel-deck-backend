package org.iesalixar.daw2.keinerhurtado.duel_deck_api.services;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.PackCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.PackDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Pack;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Expansion;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.PackRepository;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.ExpansionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackService {

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private ExpansionRepository expansionRepository;

    // Obtener todos los packs
    public List<PackDTO> getAllPacks() {
        return packRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener por ID
    public PackDTO getPackById(Long id) {
        return packRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Crear pack
    public PackDTO createPack(PackCreateDTO dto) {
        Expansion expansion = expansionRepository.findById(dto.getExpansionId())
                .orElseThrow(() -> new RuntimeException("Expansion not found"));

        if (expansion.getPack() != null) {
            throw new RuntimeException("Expansion already has a pack assigned");
        }

        Pack pack = new Pack();
        pack.setDescription(dto.getDescription());
        pack.setImage(dto.getImage());
        pack.setExpansion(expansion);

        Pack saved = packRepository.save(pack);
        return convertToDTO(saved);
    }

    // Editar pack
    public PackDTO updatePack(Long packId, PackCreateDTO dto) {
        Pack pack = packRepository.findById(packId)
                .orElseThrow(() -> new RuntimeException("Pack not found"));

        pack.setDescription(dto.getDescription());
        pack.setImage(dto.getImage());
        // pack.setExpansion(...) // solo si permites cambiar expansión

        Pack saved = packRepository.save(pack);
        return convertToDTO(saved);
    }

    // Borrar pack
    public boolean deletePack(Long packId) {
        Pack pack = packRepository.findById(packId)
                .orElseThrow(() -> new RuntimeException("Pack not found"));
        packRepository.delete(pack);
        return true;
    }

    // Conversión Entidad -> DTO
    private PackDTO convertToDTO(Pack pack) {
        PackDTO dto = new PackDTO();
        dto.setId(pack.getId());
        dto.setDescription(pack.getDescription());
        dto.setImage(pack.getImage());
        dto.setExpansionId(pack.getExpansion().getId());
        dto.setExpansionName(pack.getExpansion().getName());
        return dto;
    }
}
