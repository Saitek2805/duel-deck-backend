package org.iesalixar.daw2.keinerhurtado.duel_deck_api.services;


import org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers.ExpansionController;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.ExpansionCreateDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.ExpansionDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.entity.Expansion;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.mappers.ExpansionMapper;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.repositories.ExpansionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Locale;

@Service
public class ExpansionService  {

    // Logger para registrar la actividad del servicio
    private static final Logger logger = LoggerFactory.getLogger(ExpansionService.class);

    // Inyección de dependencias para el repositorio, mensaje, y mapper
    @Autowired
    private ExpansionRepository expansionRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ExpansionMapper expansionMapper;
    @Autowired
    private EmailService emailService;


    /**
     * Método que devuelve todas las expansiones en formato DTO.
     * @return Lista de expansiones en formato DTO.
     */
    public Page<ExpansionDTO> getAllExpansions(Pageable pageable) {
        logger.info("Solicitando la lista de todas las expansiones con paginación: página {}, tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            // Obtener todas las expansiones desde el repositorio con paginación
            Page<Expansion> expansions = expansionRepository.findAll(pageable);

            // Log de éxito indicando cuántas expansiones fueron encontradas
            logger.info("Se han encontrado {} expansiones en la página actual", expansions.getNumberOfElements());

            // Mapear las expansiones a DTO y retornarlas
            return expansions.map(expansion -> expansionMapper.toDTO(expansion)); // Aplicar el mapeo aquí

        } catch (Exception e) {
            // Log del error si ocurre una excepción
            logger.error("Error al listar las expansiones: {}", e.getMessage());
            throw new RuntimeException("Error al obtener todas las expansiones", e);
        }
    }

    /**
     * Método que busca una expansión por su ID y devuelve su DTO correspondiente.
     * @param id El ID de la expansión a buscar.
     * @return El DTO de la expansión solicitada.
     */
    public ExpansionDTO getExpansionById(Long id) {
        logger.info("Buscando expansión con ID {}", id);

        // Buscar la expansión en el repositorio
        Expansion expansion = expansionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la expansión con ID {}", id);
                    return new IllegalArgumentException("La expansión no existe.");
                });

        // Log de éxito al encontrar la expansión
        logger.info("Expansión con ID {} encontrada.", id);

        // Mapea y devuelve el DTO correspondiente
        return expansionMapper.toDTO(expansion);
    }

    /**
     * Método para crear una nueva expansión.
     * @param expansionCreateDTO Objeto DTO con los datos de la expansión a crear.
     * @param locale Locale para mensajes internacionales.
     * @return DTO de la expansión creada.
     */
    public ExpansionDTO createExpansion(ExpansionCreateDTO expansionCreateDTO, Locale locale) {
        logger.info("Creando una nueva expansión con código {}", expansionCreateDTO.getCode());

        // Verifica si ya existe una expansión con el mismo código
        if (expansionRepository.existsByCode(expansionCreateDTO.getCode())) {
            String errorMessage = messageSource.getMessage("msg.expansion-controller.insert.codeExist", null, locale);
            logger.warn("Error al crear expansión: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        // Convierte el DTO a una entidad para guardar en la base de datos
        Expansion expansion = expansionMapper.toEntity(expansionCreateDTO);

        // Guarda la expansión en el repositorio
        Expansion savedExpansion = expansionRepository.save(expansion);

        // Log de éxito indicando que la expansión fue creada correctamente
        logger.info("Expansión creada exitosamente con ID {}", savedExpansion.getId());

        try {
            String subject = "Nueva Expansión Creada: " + savedExpansion.getName();
            String body = "Se ha creado una nueva expansión con el código: " + savedExpansion.getCode() +
                    "\nNombre: " + savedExpansion.getName() +
                    "\nFecha de lanzamiento: " + savedExpansion.getReleaseDate() +
                    "\nDescripción: " + savedExpansion.getDescription();

            // Llama al servicio de correo para enviar el email (puedes definir el destinatario)
            emailService.sendEmail("duel.deck.api@gmail.com", subject, body);
        } catch (Exception e) {
            // Loguea o maneja el error en caso de que falle el envío del correo
            e.printStackTrace();  // Aquí se debería ver el error si el correo no se envía
        }


        // Devuelve el DTO de la expansión creada
        return expansionMapper.toDTO(savedExpansion);
    }

    /**
     * Método para actualizar una expansión existente.
     * @param id El ID de la expansión a actualizar.
     * @param expansionCreateDTO Objeto DTO con los nuevos datos de la expansión.
     * @param locale Locale para mensajes internacionales.
     * @return DTO de la expansión actualizada.
     */
    public ExpansionDTO updateExpansion(Long id, ExpansionCreateDTO expansionCreateDTO, Locale locale) {
        logger.info("Actualizando expansión con ID {}", id);

        // Busca la expansión existente por su ID
        Expansion existingExpansion = expansionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la expansión con ID {}", id);
                    return new IllegalArgumentException("La expansión no existe.");
                });

        // Verifica si ya existe una expansión con el mismo código pero diferente ID
        if (expansionRepository.existsExpansionByCodeAndNotId(expansionCreateDTO.getCode(), id)) {
            String errorMessage = messageSource.getMessage("msg.expansion-controller.update.codeExist", null, locale);
            logger.warn("Error al actualizar expansión: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        // Actualiza los atributos de la expansión existente
        existingExpansion.setCode(expansionCreateDTO.getCode());
        existingExpansion.setName(expansionCreateDTO.getName());
        existingExpansion.setReleaseDate(expansionCreateDTO.getReleaseDate());
        existingExpansion.setDescription(expansionCreateDTO.getDescription());

        // Guarda la expansión actualizada
        Expansion updatedExpansion = expansionRepository.save(existingExpansion);

        // Log de éxito al actualizar la expansión
        logger.info("Expansión con ID {} actualizada exitosamente.", id);

        // Devuelve el DTO de la expansión actualizada
        return expansionMapper.toDTO(updatedExpansion);
    }

    /**
     * Método para eliminar una expansión por su ID.
     * @param id El ID de la expansión a eliminar.
     */
    public void deleteExpansion(Long id) {
        logger.info("Buscando expansión con ID {}", id);

        // Busca la expansión a eliminar
        Expansion expansion = expansionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la expansión con ID {}", id);
                    return new IllegalArgumentException("La expansión no existe.");
                });

        // Elimina la expansión del repositorio
        expansionRepository.deleteById(id);

        // Log de éxito indicando que la expansión fue eliminada correctamente
        logger.info("Expansión con ID {} eliminada exitosamente.", id);
    }
}
