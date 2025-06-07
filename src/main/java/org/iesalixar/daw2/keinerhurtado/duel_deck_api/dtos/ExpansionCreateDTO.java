package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Clase DTO (Data Transfer Object) para la creación de una nueva expansión.
 * Esta clase es utilizada cuando se reciben datos para crear una nueva expansión en el sistema.
 */
@Getter  // Lombok genera el método getter para todos los campos
@Setter  // Lombok genera el método setter para todos los campos
public class ExpansionCreateDTO {

    /**
     * Código único de la expansión.
     * No puede estar vacío y debe tener un tamaño máximo de 10 caracteres.
     */
    @NotEmpty(message = "{msg.expansion.code.notEmpty}")  // Valida que el campo no esté vacío
    @Size(max = 10, message = "{msg.expansion.code.size}")  // Valida que el tamaño máximo sea 10 caracteres
    private String code;

    /**
     * Nombre de la expansión.
     * No puede estar vacío y debe tener un tamaño máximo de 100 caracteres.
     */
    @NotEmpty(message = "{msg.expansion.name.notEmpty}")  // Valida que el campo no esté vacío
    @Size(max = 100, message = "{msg.expansion.name.size}")  // Valida que el tamaño máximo sea 100 caracteres
    private String name;

    /**
     * Fecha de lanzamiento de la expansión.
     * Este campo no tiene restricciones de validación específicas.
     */
    private Date releaseDate;

    /**
     * Descripción de la expansión.
     * El tamaño máximo de la descripción es de 1000 caracteres.
     */
    @Size(max = 1000, message = "{msg.expansion.description.size}")  // Valida que el tamaño máximo sea 1000 caracteres
    private String description;
}
