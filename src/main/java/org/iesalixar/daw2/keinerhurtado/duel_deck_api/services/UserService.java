package org.iesalixar.daw2.keinerhurtado.duel_deck_api.services;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.UserDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.User;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getImage(),
                user.isEnabled()
        );
    }
    public UserDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public UserDTO updateUserEnabled(Long id, boolean enabled) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(enabled);
        User saved = userRepository.save(user);
        return convertToDTO(saved);
    }


}
