package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.UserRegisterDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Role;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.User;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.RoleRepository;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class UserRegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository; // para asignar un rol básico

    @Autowired
    private PasswordEncoder passwordEncoder; // para encriptar la contraseña

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // ¡Siempre encripta!
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setImage(dto.getImage());
        user.setEnabled(true);


        Role userRole = roleRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("Default role (id=3) not found"));
        user.setRoles(Collections.singleton(userRole));


        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
