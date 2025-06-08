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
@ToString(exclude = {"type", "typing", "attribute", "rarity", "expansion"})
@EqualsAndHashCode(exclude = {"type", "typing", "attribute", "rarity", "expansion"})
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 10)
    private String code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private CardType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typing_id")
    private CardTyping typing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id")
    private CardAttribute attribute;

    @Column(name = "level")
    private Integer level;

    @Column(name = "attack")
    private Integer attack;

    @Column(name = "defense")
    private Integer defense;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rarity_id")
    private CardRarity rarity;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expansion_id", nullable = false)
    private Expansion expansion;

    public Card(String code, String name, CardType type, CardTyping typing, CardAttribute attribute, Integer level,
                Integer attack, Integer defense, CardRarity rarity, String image, String description, Expansion expansion) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.typing = typing;
        this.attribute = attribute;
        this.level = level;
        this.attack = attack;
        this.defense = defense;
        this.rarity = rarity;
        this.image = image;
        this.description = description;
        this.expansion = expansion;
    }
}