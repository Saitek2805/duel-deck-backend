package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String image; // opcional
}
