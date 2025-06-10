package org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "deck_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeckComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String content;
}
