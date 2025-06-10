package org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "card_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardStatus {

    @Id
    @Column(name = "card_id")
    private Long cardId; // FK y PK

    @OneToOne
    @MapsId
    @JoinColumn(name = "card_id")
    private Card card;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public enum Status {
        Forbidden,
        Limited,
        Semi_Limited,
        Unlimited
    }
}
