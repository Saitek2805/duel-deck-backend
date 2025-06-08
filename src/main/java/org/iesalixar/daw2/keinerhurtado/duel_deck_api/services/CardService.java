package org.iesalixar.daw2.keinerhurtado.duel_deck_api.services;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.CardCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.CardDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.*;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.mappers.CardMapper;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class CardService {

    private static final Logger logger = LoggerFactory.getLogger(CardService.class);

    @Autowired private CardRepository cardRepository;
    @Autowired private ExpansionRepository expansionRepository;
    @Autowired private CardTypeRepository cardTypeRepository;
    @Autowired private CardTypingRepository cardTypingRepository;
    @Autowired private CardAttributeRepository cardAttributeRepository;
    @Autowired private CardRarityRepository cardRarityRepository;
    @Autowired private CardMapper cardMapper;
    @Autowired private MessageSource messageSource;

    public Page<CardDTO> getAllCards(Pageable pageable) {
        logger.info("Obteniendo cartas paginadas");
        return cardRepository.findAll(pageable).map(cardMapper::toDTO);
    }

    public CardDTO getCardById(Long id) {
        logger.info("Buscando carta con ID {}", id);
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La carta no existe."));
        return cardMapper.toDTO(card);
    }

    public CardDTO createCard(CardCreateDTO cardCreateDTO, Locale locale) {
        logger.info("Creando carta con código {}", cardCreateDTO.getCode());

        if (cardRepository.existsByCode(cardCreateDTO.getCode())) {
            throw new IllegalArgumentException(
                    messageSource.getMessage("msg.card-controller.insert.codeExist", null, locale));
        }

        Expansion expansion = expansionRepository.findById(cardCreateDTO.getExpansion())
                .orElseThrow(() -> new IllegalArgumentException(
                        messageSource.getMessage("msg.card-controller.insert.expansionNotFound", null, locale)));

        CardType type = cardTypeRepository.findByName(cardCreateDTO.getType())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de carta no válido."));

        CardTyping typing = cardTypingRepository.findByName(cardCreateDTO.getTyping())
                .orElseThrow(() -> new IllegalArgumentException("Tipado de carta no válido."));

        CardAttribute attribute = cardAttributeRepository.findByName(cardCreateDTO.getAttribute())
                .orElseThrow(() -> new IllegalArgumentException("Atributo de carta no válido."));

        CardRarity rarity = cardRarityRepository.findByName(cardCreateDTO.getRarity())
                .orElseThrow(() -> new IllegalArgumentException("Rareza de carta no válida."));

        Card card = cardMapper.toEntity(cardCreateDTO, type, typing, attribute, rarity, expansion);
        Card savedCard = cardRepository.save(card);
        return cardMapper.toDTO(savedCard);
    }

    public CardDTO updateCard(Long id, CardCreateDTO cardCreateDTO, Locale locale) {
        logger.info("Actualizando carta con ID {}", id);

        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La carta no existe."));

        if (cardRepository.existsCardByCodeAndNotId(cardCreateDTO.getCode(), id)) {
            throw new IllegalArgumentException(
                    messageSource.getMessage("msg.card-controller.update.codeExist", null, locale));
        }

        Expansion expansion = expansionRepository.findById(cardCreateDTO.getExpansion())
                .orElseThrow(() -> new IllegalArgumentException(
                        messageSource.getMessage("msg.card-controller.update.expansionNotFound", null, locale)));

        CardType type = cardTypeRepository.findByName(cardCreateDTO.getType())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de carta no válido."));

        CardTyping typing = cardTypingRepository.findByName(cardCreateDTO.getTyping())
                .orElseThrow(() -> new IllegalArgumentException("Tipado de carta no válido."));

        CardAttribute attribute = cardAttributeRepository.findByName(cardCreateDTO.getAttribute())
                .orElseThrow(() -> new IllegalArgumentException("Atributo de carta no válido."));

        CardRarity rarity = cardRarityRepository.findByName(cardCreateDTO.getRarity())
                .orElseThrow(() -> new IllegalArgumentException("Rareza de carta no válida."));

        card.setCode(cardCreateDTO.getCode());
        card.setName(cardCreateDTO.getName());
        card.setType(type);
        card.setTyping(typing);
        card.setAttribute(attribute);
        card.setLevel(cardCreateDTO.getLevel());
        card.setAttack(cardCreateDTO.getAttack());
        card.setDefense(cardCreateDTO.getDefense());
        card.setRarity(rarity);
        card.setImage(cardCreateDTO.getImage());
        card.setDescription(cardCreateDTO.getDescription());
        card.setExpansion(expansion);

        Card updatedCard = cardRepository.save(card);
        return cardMapper.toDTO(updatedCard);
    }

    public void deleteCard(Long id) {
        logger.info("Eliminando carta con ID {}", id);

        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La carta no existe."));

        cardRepository.delete(card);
        logger.info("Carta con ID {} eliminada.", id);
    }
}
