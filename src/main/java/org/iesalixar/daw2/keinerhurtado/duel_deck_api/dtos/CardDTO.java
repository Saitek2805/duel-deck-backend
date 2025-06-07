package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO (Data Transfer Object) para la entidad Carta.
 * Representa los datos de una carta que serán transferidos entre el servidor y el cliente.
 */
@Data  // Lombok genera automáticamente los métodos getter, setter, equals, hashCode, y toString
@AllArgsConstructor  // Lombok genera un constructor con todos los parámetros
@NoArgsConstructor   // Lombok genera un constructor sin parámetros
public class CardDTO {

    /**
     * Identificador único de la carta.
     * Este ID es utilizado para identificar de manera única la carta en el sistema.
     */
    private Long id;

    /**
     * Código único de la carta.
     * Se utiliza para identificar la carta, por ejemplo, en colecciones o decks.
     */
    private String code;

    /**
     * Nombre de la carta.
     * Es el nombre que se le asigna a la carta en el sistema.
     */
    private String name;

    /**
     * Tipo de la carta.
     * Puede ser de diferentes tipos, como criatura, hechizo, artefacto, etc.
     */
    private String type;

    /**
     * Rareza de la carta.
     * Indica la rareza de la carta, como común, rara, épica, etc.
     */
    private String rarity;

    /**
     * Expansión a la que pertenece la carta.
     * Se refiere a la expansión del juego a la que la carta está asociada.
     * Utiliza el DTO de la clase `ExpansionDTO` para representar los datos de la expansión.
     */
    private ExpansionDTO expansion;
}
