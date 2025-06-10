package org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Expansion;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repositorio
public interface PackRepository extends JpaRepository<Pack, Long> {
    Optional<Pack> findByExpansion(Expansion expansion);
    Optional<Pack> findByExpansion_Code(String code); // si buscas por código
}
