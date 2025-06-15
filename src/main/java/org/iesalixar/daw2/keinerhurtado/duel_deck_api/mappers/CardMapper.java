package org.iesalixar.daw2.keinerhurtado.duel_deck_api.mappers;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.CardCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.CardDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.ExpansionDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.*;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    public CardDTO toDTO(Card card) {
        if (card == null) {
            return null;
        }

        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(card.getId());
        cardDTO.setCode(card.getCode());
        cardDTO.setName(card.getName());

        if (card.getType() != null) {
            cardDTO.setType(card.getType().getName());
        }

        if (card.getTyping() != null) {
            cardDTO.setTyping(card.getTyping().getName());
        }

        if (card.getAttribute() != null) {
            cardDTO.setAttribute(card.getAttribute().getName());
        }

        cardDTO.setLevel(card.getLevel());
        cardDTO.setAttack(card.getAttack());
        cardDTO.setDefense(card.getDefense());

        if (card.getRarity() != null) {
            cardDTO.setRarity(card.getRarity().getName());
        }

        cardDTO.setImage(card.getImage());
        cardDTO.setDescription(card.getDescription());


        if (card.getExpansion() != null) {
            Expansion expansion = card.getExpansion();
            ExpansionDTO expansionDTO = new ExpansionDTO(
                    expansion.getId(),
                    expansion.getCode(),
                    expansion.getName(),
                    expansion.getReleaseDate(),
                    expansion.getDescription(),
                    expansion.getImage()
            );
            cardDTO.setExpansion(expansionDTO);
        }

        return cardDTO;
    }

    public Card toEntity(CardCreateDTO createDTO, CardType type, CardTyping typing,
                         CardAttribute attribute, CardRarity rarity, Expansion expansion) {

        if (createDTO == null) {
            return null;
        }

        Card card = new Card();
        card.setCode(createDTO.getCode());
        card.setName(createDTO.getName());
        card.setType(type);
        card.setTyping(typing);
        card.setAttribute(attribute);
        card.setLevel(createDTO.getLevel());
        card.setAttack(createDTO.getAttack());
        card.setDefense(createDTO.getDefense());
        card.setRarity(rarity);
        card.setImage(createDTO.getImage());
        card.setDescription(createDTO.getDescription());
        card.setExpansion(expansion);

        return card;
    }
}
