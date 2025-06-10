package org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String image;
    private boolean enabled;
}
