package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Clase DTO (Data Transfer Object) para la entidad Expansión.
 * Representa los datos que se enviarán y recibirán en las respuestas de la API
 * relacionados con una expansión de un juego.
 */
@Data  // Genera automáticamente los métodos getter, setter, equals, hashCode, y toString
@AllArgsConstructor  // Genera un constructor con todos los parámetros
@NoArgsConstructor   // Genera un constructor sin parámetros
public class ExpansionDTO {

    // Identificador único de la expansión
    private Long id;

    // Código único de la expansión, que generalmente se usa para identificarla
    private String code;

    // Nombre de la expansión
    private String name;

    // Fecha de lanzamiento de la expansión
    private Date releaseDate;

    // Descripción de la expansión, que da más detalles sobre la misma
    private String description;
}
