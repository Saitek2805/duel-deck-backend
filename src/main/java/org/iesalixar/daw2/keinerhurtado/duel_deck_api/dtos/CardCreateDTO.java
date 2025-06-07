package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Clase DTO (Data Transfer Object) para la creación de una nueva carta.
 * Esta clase se utiliza para recibir los datos de una nueva carta cuando el cliente la crea.
 */
@Data  // Lombok genera automáticamente los métodos getter, setter, equals, hashCode, y toString
public class CardCreateDTO {

    /**
     * Código único de la carta.
     * No puede estar vacío y debe tener un tamaño máximo de 10 caracteres.
     */
    @NotEmpty(message = "{msg.card.code.notEmpty}")  // Valida que el campo no esté vacío
    @Size(max = 10, message = "{msg.card.code.size}")  // Valida que el tamaño máximo sea 10 caracteres
    private String code;

    /**
     * Nombre de la carta.
     * No puede estar vacío y debe tener un tamaño máximo de 100 caracteres.
     */
    @NotEmpty(message = "{msg.card.name.notEmpty}")  // Valida que el campo no esté vacío
    @Size(max = 100, message = "{msg.card.name.size}")  // Valida que el tamaño máximo sea 100 caracteres
    private String name;

    /**
     * Tipo de carta.
     * El tipo de la carta puede tener un máximo de 50 caracteres.
     */
    @Size(max = 50, message = "{msg.card.cardType.size}")  // Valida que el tamaño máximo sea 50 caracteres
    private String type;

    /**
     * Rareza de la carta.
     * La rareza de la carta puede tener un máximo de 50 caracteres.
     */
    @Size(max = 50, message = "{msg.card.rarity.size}")  // Valida que el tamaño máximo sea 50 caracteres
    private String rarity;

    /**
     * Identificador de la expansión a la que pertenece la carta.
     * Este campo hace referencia al ID de la expansión a la que la carta está asociada.
     * No tiene restricciones de validación específicas.
     */
    private Long expansion;
}
