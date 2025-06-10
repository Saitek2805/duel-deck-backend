package org.iesalixar.daw2.keinerhurtado.duel_deck_api.services;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.DeckCardCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.DeckCardDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.*;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeckCardService {

    @Autowired
    private DeckCardRepository deckCardRepository;

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private CardRepository cardRepository;

    // Listar cartas de un mazo
    public List<DeckCardDTO> getCardsInDeck(Long deckId) {
        return deckCardRepository.findByDeck_Id(deckId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Añadir una carta al mazo (o actualizar cantidad si ya existe)
    public DeckCardDTO addOrUpdateDeckCard(DeckCardCreateDTO dto) {
        Deck deck = deckRepository.findById(dto.getDeckId())
                .orElseThrow(() -> new RuntimeException("Deck not found"));
        Card card = cardRepository.findById(dto.getCardId())
                .orElseThrow(() -> new RuntimeException("Card not found"));

        DeckCardId id = new DeckCardId(dto.getDeckId(), dto.getCardId());
        DeckCard deckCard = deckCardRepository.findById(id)
                .orElse(new DeckCard(deck, card, dto.getQuantity()));

        deckCard.setQuantity(dto.getQuantity());

        DeckCard saved = deckCardRepository.save(deckCard);
        return convertToDTO(saved);
    }

    // Eliminar una carta de un mazo
    public void deleteDeckCard(Long deckId, Long cardId) {
        DeckCardId id = new DeckCardId(deckId, cardId);
        if (!deckCardRepository.existsById(id)) {
            throw new RuntimeException("DeckCard not found");
        }
        deckCardRepository.deleteById(id);
    }

    // Obtener una carta concreta de un mazo
    public DeckCardDTO getDeckCard(Long deckId, Long cardId) {
        DeckCardId id = new DeckCardId(deckId, cardId);
        return deckCardRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Conversión entidad a DTO
    private DeckCardDTO convertToDTO(DeckCard deckCard) {
        return new DeckCardDTO(
                deckCard.getDeck().getId(),
                deckCard.getCard().getId(),
                deckCard.getCard().getName(),
                deckCard.getQuantity()
        );
    }
    public List<DeckCardDTO> getAllDeckCards() {
        return deckCardRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<DeckCardDTO> addOrUpdateDeckCards(List<DeckCardCreateDTO> dtos) {
        return dtos.stream()
                .map(this::addOrUpdateDeckCard)
                .collect(Collectors.toList());
    }

}
