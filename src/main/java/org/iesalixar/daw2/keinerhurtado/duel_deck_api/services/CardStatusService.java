package org.iesalixar.daw2.keinerhurtado.duel_deck_api.services;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.CardStatusDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Card;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.CardStatus;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.CardStatus.Status;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.CardRepository;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.CardStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CardStatusService {

    @Autowired
    private CardStatusRepository cardStatusRepository;

    @Autowired
    private CardRepository cardRepository;

    // Cambia el estado de la carta, crea uno nuevo si no existe
    public CardStatusDTO changeStatus(Long cardId, String newStatus) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        Status statusEnum;
        switch (newStatus) {
            case "Forbidden":
                statusEnum = Status.Forbidden; break;
            case "Limited":
                statusEnum = Status.Limited; break;
            case "Unlimited":
                statusEnum = Status.Unlimited; break;
            case "Semi_Limited":
                statusEnum = Status.Semi_Limited; break;
            default:
                throw new IllegalArgumentException("Invalid status");
        }

        CardStatus cardStatus = cardStatusRepository.findById(cardId)
                .orElse(new CardStatus(cardId, card, statusEnum));
        cardStatus.setStatus(statusEnum);
        cardStatus.setCard(card);

        CardStatus saved = cardStatusRepository.save(cardStatus);

        return new CardStatusDTO(saved.getCardId(), card.getName(), saved.getStatus().name().replace("_", "-"));
    }
    public CardStatusDTO getStatus(Long cardId) {
        return cardStatusRepository.findById(cardId)
                .map(cardStatus -> new CardStatusDTO(
                        cardStatus.getCardId(),
                        cardStatus.getCard().getName(),
                        cardStatus.getStatus().name().replace("_", "-")
                ))
                .orElseThrow(() -> new RuntimeException("Card status not found"));
    }

    public List<CardStatusDTO> getAllStatuses() {
        return cardStatusRepository.findAll()
                .stream()
                .map(cs -> new CardStatusDTO(
                        cs.getCardId(),
                        cs.getCard().getName(),
                        cs.getStatus().name().replace("_", "-")
                ))
                .collect(Collectors.toList());
    }

    public void deleteStatus(Long cardId) {
        if (!cardStatusRepository.existsById(cardId)) {
            throw new RuntimeException("Card status not found");
        }
        cardStatusRepository.deleteById(cardId);
    }

}
