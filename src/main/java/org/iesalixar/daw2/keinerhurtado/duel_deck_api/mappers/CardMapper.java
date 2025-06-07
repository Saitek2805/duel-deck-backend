    package org.iesalixar.daw2.keinerhurtado.duel_deck_api.mappers;

    import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.CardCreateDTO;
    import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.CardDTO;
    import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.ExpansionDTO;
    import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Card;
    import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Expansion;
    import org.springframework.stereotype.Component;

    /**
     * Mapper para convertir entre entidades y DTOs de cartas.
     */
    @Component
    public class CardMapper {

        /**
         * Convierte una entidad Card en un DTO CardDTO.
         *
         * @param card La entidad Card a convertir.
         * @return Un objeto CardDTO o null si la entrada es null.
         */
        public CardDTO toDTO(Card card){
            if (card == null){
                return null;
            }
            CardDTO cardDTO = new CardDTO();
            cardDTO.setId(card.getId());
            cardDTO.setCode(card.getCode());
            cardDTO.setName(card.getName());
//            cardDTO.setType(card.getType());
//            cardDTO.setRarity(card.getRarity());

            if (card.getExpansion() != null){
                Expansion expansion = card.getExpansion();
                ExpansionDTO expansionDTO = new ExpansionDTO(
                        expansion.getId(),
                        expansion.getCode(),
                        expansion.getName(),
                        expansion.getReleaseDate(),
                        expansion.getDescription()
                );
                cardDTO.setExpansion(expansionDTO);
            }
            return cardDTO;
        }

        /**
         * Convierte un DTO CardCreateDTO en una entidad Card.
         *
         * @param createDTO DTO con la información de la carta.
         * @param expansion Expansión asociada a la carta.
         * @return Una entidad Card o null si la entrada es null.
         */
        public Card toEntity(CardCreateDTO createDTO, Expansion expansion){
            if (createDTO == null){
                return null;
            }
            Card card = new Card();
            card.setCode(createDTO.getCode());
            card.setName(createDTO.getName());
//            card.setType(createDTO.getType());
//            card.setRarity(createDTO.getRarity());
            card.setExpansion(expansion);

            return card;
        }
    }
