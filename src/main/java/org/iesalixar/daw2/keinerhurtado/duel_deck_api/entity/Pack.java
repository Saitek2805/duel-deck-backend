package org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa un sobre asociado a una expansión en el sistema Duel Deck.
 * Cada sobre tiene una relación uno a uno con una expansión.
 */
@Entity
@Table(name = "packs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"expansion"})
@EqualsAndHashCode(exclude = {"expansion"})
public class Pack {

    /**
     * Identificador único del sobre.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Imagen del sobre.
     */
    @Column(name = "image", length = 255)
    private String image;

    /**
     * Descripción del sobre.
     */
    @Column(name = "description", length = 255)
    private String description;

    /**
     * Expansión a la que pertenece este sobre.
     * Relación uno a uno.
     */
    @OneToOne
    @JoinColumn(name = "expansion_id", nullable = false, unique = true)
    private Expansion expansion;

    /**
     * Constructor con campos clave.
     */
    public Pack(String image, String description, Expansion expansion) {
        this.image = image;
        this.description = description;
        this.expansion = expansion;
    }
}
