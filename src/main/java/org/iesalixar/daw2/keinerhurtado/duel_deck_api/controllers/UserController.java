package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.UserDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.UserEnabledDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        UserDTO user = userService.getUserByUsername(username);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }
    @PutMapping("/{id}/enabled")
    public ResponseEntity<UserDTO> updateUserEnabled(
            @PathVariable Long id,
            @RequestBody UserEnabledDTO enabledDTO) {
        try {
            UserDTO updated = userService.updateUserEnabled(id, enabledDTO.isEnabled());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/username/{username}")
    public ResponseEntity<UserDTO> updateUserByUsername(
            @PathVariable String username,
            @RequestBody UserDTO dto) {
        try {
            UserDTO updated = userService.updateUserByUsername(username, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
