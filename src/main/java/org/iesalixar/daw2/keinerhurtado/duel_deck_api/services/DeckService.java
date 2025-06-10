package org.iesalixar.daw2.keinerhurtado.duel_deck_api.services;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.DeckCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.DeckDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Deck;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.User;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.DeckRepository;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeckService {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private UserRepository userRepository;

    // Listar todos los mazos
    public List<DeckDTO> getAllDecks() {
        return deckRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Listar mazos por usuario
    public List<DeckDTO> getDecksByUserId(Long userId) {
        return deckRepository.findByUser_Id(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener un mazo por id
    public DeckDTO getDeckById(Long id) {
        Optional<Deck> deck = deckRepository.findById(id);
        return deck.map(this::convertToDTO).orElse(null);
    }

    // Crear un mazo
    public DeckDTO createDeck(DeckCreateDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Deck deck = new Deck();
        deck.setName(dto.getName());
        deck.setDescription(dto.getDescription());
        deck.setImage(dto.getImage());
        deck.setUser(user);

        Deck saved = deckRepository.save(deck);
        return convertToDTO(saved);
    }

    // Editar un mazo
    public DeckDTO updateDeck(Long id, DeckCreateDTO dto) {
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deck not found"));

        deck.setName(dto.getName());
        deck.setDescription(dto.getDescription());
        deck.setImage(dto.getImage());
        // No se suele permitir cambiar el usuario del mazo, pero podrías hacerlo aquí si lo necesitas

        Deck saved = deckRepository.save(deck);
        return convertToDTO(saved);
    }

    // Borrar un mazo
    public void deleteDeck(Long id) {
        if (!deckRepository.existsById(id)) {
            throw new RuntimeException("Deck not found");
        }
        deckRepository.deleteById(id);
    }

    // Conversor entidad -> DTO
    private DeckDTO convertToDTO(Deck deck) {
        DeckDTO dto = new DeckDTO();
        dto.setId(deck.getId());
        dto.setName(deck.getName());
        dto.setDescription(deck.getDescription());
        dto.setImage(deck.getImage());
        dto.setUserId(deck.getUser().getId());
        dto.setUserName(deck.getUser().getUsername());
        return dto;
    }

}
