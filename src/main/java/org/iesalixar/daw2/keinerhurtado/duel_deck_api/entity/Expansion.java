package org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "expansions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"cards", "pack"})
@EqualsAndHashCode(exclude = {"cards", "pack"})
public class Expansion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 10)
    private String code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "image", length = 255)
    private String image;

    @OneToMany(mappedBy = "expansion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards;

    @OneToOne(mappedBy = "expansion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Pack pack;

    public Expansion(String code, String name, Date releaseDate, String description, String image) {
        this.code = code;
        this.name = name;
        this.releaseDate = releaseDate;
        this.description = description;
        this.image = image;
    }
}
