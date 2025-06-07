package org.iesalixar.daw2.keinerhurtado.duel_deck_api.services;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.CardCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.CardDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Card;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Expansion;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.mappers.CardMapper;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.CardRepository;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.ExpansionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar la lógica de negocio de las cartas.
 */
@Service
public class CardService {
    private static final Logger logger = LoggerFactory.getLogger(CardService.class);

    @Autowired
    private CardRepository cardRepository; // Repositorio de Card

    @Autowired
    private ExpansionRepository expansionRepository; // Repositorio de Expansion

    @Autowired
    private MessageSource messageSource; // Para los mensajes de localización

    @Autowired
    private CardMapper cardMapper; // Mapeador de entidades a DTOs y viceversa

    /**
     * Obtiene todas las cartas disponibles en la base de datos.
     *
     * @return Lista de CardDTO con todas las cartas.
     */
    public Page<CardDTO> getAllCards(Pageable pageable) {
        logger.info("Obteniendo cartas paginadas con parámetros: página={}, tamaño={}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Card> page = cardRepository.findAll(pageable);
            return page.map(cardMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener cartas paginadas: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene una carta específica por su ID.
     *
     * @param id ID de la carta a buscar.
     * @return CardDTO de la carta encontrada.
     */
    public CardDTO getCardById(Long id) {
        logger.info("Buscando carta con ID {}", id);
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la carta con ID {}", id);
                    return new IllegalArgumentException("La carta no existe.");
                });
        logger.info("Carta con ID {} encontrada.", id);
        return cardMapper.toDTO(card);
    }

    /**
     * Crea una nueva carta en la base de datos.
     *
     * @param cardCreateDTO Datos de la nueva carta.
     * @param locale Idioma para los mensajes de error.
     * @return CardDTO de la carta creada.
     */
    public CardDTO createCard(CardCreateDTO cardCreateDTO, Locale locale) {
        logger.info("Creando una nueva carta con código {}", cardCreateDTO.getCode());

        if (cardRepository.existsByCode(cardCreateDTO.getCode())) {
            String errorMessage = messageSource.getMessage("msg.card-controller.insert.codeExist", null, locale);
            logger.warn("Error al crear carta: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        Expansion expansion = expansionRepository.findById(cardCreateDTO.getExpansion())
                .orElseThrow(() -> {
                    String errorMessage = messageSource.getMessage("msg.card-controller.insert.expansionNotFound", null, locale);
                    logger.warn("Error al crear carta: {}", errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });

        Card card = cardMapper.toEntity(cardCreateDTO, expansion);
        Card savedCard = cardRepository.save(card);
        logger.info("Carta creada exitosamente con ID {}", savedCard.getId());
        return cardMapper.toDTO(savedCard);
    }

    /**
     * Actualiza una carta existente en la base de datos.
     *
     * @param id ID de la carta a actualizar.
     * @param cardCreateDTO Nuevos datos de la carta.
     * @param locale Idioma para los mensajes de error.
     * @return CardDTO de la carta actualizada.
     */
    public CardDTO updateCard(Long id, CardCreateDTO cardCreateDTO, Locale locale) {
        logger.info("Actualizando carta con ID {}", id);

        Card existingCard = cardRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la carta con ID {}", id);
                    return new IllegalArgumentException("La carta no existe.");
                });

        if (cardRepository.existsCardByCodeAndNotId(cardCreateDTO.getCode(), id)) {
            String errorMessage = messageSource.getMessage("msg.card-controller.update.codeExist", null, locale);
            logger.warn("Error al actualizar carta: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        Expansion expansion = expansionRepository.findById(cardCreateDTO.getExpansion())
                .orElseThrow(() -> {
                    String errorMessage = messageSource.getMessage("msg.card-controller.update.expansionNotFound", null, locale);
                    logger.warn("Error al actualizar carta: {}", errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });

        existingCard.setCode(cardCreateDTO.getCode());
        existingCard.setName(cardCreateDTO.getName());
//        existingCard.setType(cardCreateDTO.getType());
//        existingCard.setRarity(cardCreateDTO.getRarity());
        existingCard.setExpansion(expansion);
        Card updatedCard = cardRepository.save(existingCard);
        logger.info("Carta con ID {} actualizada exitosamente.", id);
        return cardMapper.toDTO(updatedCard);
    }

    /**
     * Elimina una carta de la base de datos por su ID.
     *
     * @param id ID de la carta a eliminar.
     */
    public void deleteCard(Long id) {
        logger.info("Buscando carta con ID {}", id);

        Card card = cardRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la carta con ID {}", id);
                    return new IllegalArgumentException("La carta no existe.");
                });

        cardRepository.deleteById(id);
        logger.info("Carta con ID {} eliminada exitosamente.", id);
    }
}
