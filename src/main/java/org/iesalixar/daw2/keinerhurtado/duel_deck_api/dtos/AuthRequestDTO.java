package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.Getter;

/**
 * Clase DTO (Data Transfer Object) para la solicitud de autenticación.
 * Esta clase se utiliza para recibir las credenciales del usuario (nombre de usuario y contraseña)
 * cuando el cliente realiza una solicitud de inicio de sesión.
 */
@Getter  // Lombok genera automáticamente los métodos getter para todos los campos
public class AuthRequestDTO {

    /**
     * Nombre de usuario del usuario que intenta autenticarse.
     * El cliente envía este valor junto con la contraseña para iniciar sesión.
     */
    private String username;

    /**
     * Contraseña del usuario que intenta autenticarse.
     * El cliente envía este valor junto con el nombre de usuario para iniciar sesión.
     */
    private String password;
}
