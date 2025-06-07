package org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories;

import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para gestionar operaciones de acceso a datos para la entidad Card.
 */
public interface CardRepository extends JpaRepository<Card, Long> {

    /**
     * Verifica si ya existe una carta con el código especificado en la base de datos.
     *
     * @param code Código de la carta a verificar.
     * @return true si el código ya existe, false en caso contrario.
     */
    boolean existsByCode(String code);

    /**
     * Verifica si ya existe una carta con el código especificado en la base de datos,
     * pero que no pertenezca a la carta con el ID proporcionado.
     *
     * @param code Código de la carta a verificar.
     * @param id ID de la carta que se excluye de la verificación.
     * @return true si el código ya existe en otra carta, false en caso contrario.
     */
    @Query("SELECT COUNT(c) > 0 FROM Card c WHERE c.code = :code AND c.id != :id")
    boolean existsCardByCodeAndNotId(@Param("code") String code, @Param("id") Long id);

    /**
     * Obtiene una lista con todas las cartas almacenadas en la base de datos.
     *
     * @return Lista de todas las cartas.
     */
    List<Card> findAll();
}