package org.iesalixar.daw2.keinerhurtado.duel_deck_api.services;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.DeckCommentCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.DeckCommentDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Deck;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.DeckComment;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.User;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.DeckCommentRepository;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.DeckRepository;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeckCommentService {

    @Autowired
    private DeckCommentRepository deckCommentRepository;

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private UserRepository userRepository;

    // Crear comentario
    public DeckCommentDTO createComment(DeckCommentCreateDTO dto) {
        Deck deck = deckRepository.findById(dto.getDeckId())
                .orElseThrow(() -> new RuntimeException("Deck not found"));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DeckComment comment = new DeckComment();
        comment.setDeck(deck);
        comment.setUser(user);
        comment.setContent(dto.getContent());

        DeckComment saved = deckCommentRepository.save(comment);
        return convertToDTO(saved);
    }

    // Obtener todos los comentarios
    public List<DeckCommentDTO> getAllComments() {
        return deckCommentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Listar comentarios por mazo
    public List<DeckCommentDTO> getCommentsByDeck(Long deckId) {
        return deckCommentRepository.findByDeck_Id(deckId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Listar comentarios por usuario
    public List<DeckCommentDTO> getCommentsByUser(Long userId) {
        return deckCommentRepository.findByUser_Id(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener un comentario por id
    public DeckCommentDTO getCommentById(Long id) {
        Optional<DeckComment> comment = deckCommentRepository.findById(id);
        return comment.map(this::convertToDTO).orElse(null);
    }

    // Borrar un comentario
    public void deleteComment(Long id) {
        if (!deckCommentRepository.existsById(id)) {
            throw new RuntimeException("Comment not found");
        }
        deckCommentRepository.deleteById(id);
    }

    // Conversor entidad -> DTO
    private DeckCommentDTO convertToDTO(DeckComment comment) {
        return new DeckCommentDTO(
                comment.getId(),
                comment.getDeck().getId(),
                comment.getDeck().getName(),
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getContent()
        );
    }
}
