package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase DTO (Data Transfer Object) para la respuesta de autenticación.
 * Esta clase se utiliza para enviar la respuesta de autenticación al cliente después de un inicio de sesión exitoso.
 */
@Data  // Lombok genera automáticamente los métodos getter, setter, equals, hashCode, y toString
@AllArgsConstructor  // Lombok genera un constructor con todos los parámetros
public class AuthResponseDTO {

    /**
     * Token de autenticación generado después de un inicio de sesión exitoso.
     * Este token es utilizado para autenticar al usuario en solicitudes subsecuentes.
     */
    private String token;

    /**
     * Mensaje asociado con la respuesta de autenticación.
     * Este mensaje puede ser una notificación de éxito o información adicional para el usuario.
     */
    private String message;
}
