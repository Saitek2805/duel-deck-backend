package org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Representa una carta en el sistema Duel Deck.
 * Contiene información sobre el código, nombre, tipo, rareza y la expansión a la que pertenece.
 */
@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"expansion"})
@EqualsAndHashCode(exclude = {"expansion"})
public class Card {

    /**
     * Identificador único de la carta.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Código único de la carta.
     */
    @Column(name = "code", nullable = false, unique = true, length = 10)
    private String code;

    /**
     * Nombre de la carta.
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Tipo de carta (Monstruo, Mágica, Trampa, etc.).
     */
//    @Column(name = "type", length = 50)
//    private String type;

    /**
     * Rareza de la carta.
     */
//    @Column(name = "rarity", length = 50)
//    private String rarity;

    /**
     * Expansión a la que pertenece la carta.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expansion_id", referencedColumnName = "id", nullable = false)
    private Expansion expansion;

    /**
     * Constructor para crear una carta con parámetros específicos.
     *
     * @param code Código único de la carta.
     * @param name Nombre de la carta.
     * @param type Tipo de carta (Monstruo, Mágica, Trampa, etc.).
     * @param rarity Rareza de la carta.
     * @param expansion Expansión a la que pertenece la carta.
     */
    public Card(String code, String name, String type, String rarity, Expansion expansion) {
        this.code = code;
        this.name = name;
//        this.type = type;
//        this.rarity = rarity;
        this.expansion = expansion;
    }
}
