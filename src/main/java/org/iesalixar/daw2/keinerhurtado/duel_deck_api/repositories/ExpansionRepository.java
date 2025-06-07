package org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Expansion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Expansion que extiende JpaRepository.
 * Proporciona operaciones CRUD y consultas personalizadas para la entidad Expansion.
 */
public interface ExpansionRepository extends JpaRepository<Expansion, Long> {

    /**
     * Obtiene todas las expansiones almacenadas en la base de datos.
     *
     * @return Lista de todas las expansiones.
     */
    List<Expansion> findAll();

    /**
     * Guarda una nueva expansión o actualiza una existente.
     *
     * @param expansion Expansión a guardar o actualizar.
     * @return La expansión guardada o actualizada.
     */
    Expansion save(Expansion expansion);

    /**
     * Elimina una expansión por su ID.
     *
     * @param id ID de la expansión a eliminar.
     */
    void deleteById(Long id);

    /**
     * Obtiene una expansión por su ID.
     *
     * @param id ID de la expansión a buscar.
     * @return Un Optional que contiene la expansión si se encuentra.
     */
    Optional<Expansion> findById(Long id);

    /**
     * Verifica si ya existe una expansión con el código especificado en la base de datos.
     *
     * @param code Código de la expansión a verificar.
     * @return true si el código ya existe, false en caso contrario.
     */
    boolean existsByCode(String code);

    /**
     * Verifica si ya existe una expansión con el código especificado en la base de datos,
     * pero que no pertenezca a la expansión con el ID proporcionado.
     *
     * @param code Código de la expansión a verificar.
     * @param id ID de la expansión que se excluye de la verificación.
     * @return true si el código ya existe en otra expansión, false en caso contrario.
     */
    @Query("SELECT COUNT(e) > 0 FROM Expansion e WHERE e.code = :code AND e.id != :id")
    boolean existsExpansionByCodeAndNotId(@Param("code") String code, @Param("id") Long id);
}
