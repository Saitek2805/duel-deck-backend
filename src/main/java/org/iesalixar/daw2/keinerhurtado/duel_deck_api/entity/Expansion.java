package org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Card;

import java.util.Date;
import java.util.List;

/**
 * Representa una expansión en el sistema Duel Deck.
 * Contiene información sobre el código, nombre, fecha de lanzamiento y descripción de la expansión.
 */
@Entity
@Table(name = "expansions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"cards"})
@EqualsAndHashCode(exclude = {"cards"})
public class Expansion {

    /**
     * Identificador único de la expansión.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Código único de la expansión.
     */
    @Column(name = "code", nullable = false, unique = true, length = 10)
    private String code;

    /**
     * Nombre de la expansión.
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Fecha de lanzamiento de la expansión.
     */
    @Column(name = "release_date")
    private Date releaseDate;

    /**
     * Descripción de la expansión.
     */
    @Column(name = "description", length = 1000)
    private String description;

    /**
     * Lista de cartas asociadas a la expansión.
     */
    @OneToMany(mappedBy = "expansion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards;

    /**
     * Constructor para inicializar una expansión con parámetros específicos.
     *
     * @param code Código único de la expansión.
     * @param name Nombre de la expansión.
     * @param releaseDate Fecha de lanzamiento de la expansión.
     * @param description Descripción de la expansión.
     */
    public Expansion(String code, String name, Date releaseDate, String description) {
        this.code = code;
        this.name = name;
        this.releaseDate = releaseDate;
        this.description = description;
    }
}
