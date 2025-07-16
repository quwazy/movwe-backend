package movwe.utils.interfaces;

import java.util.List;

/**
 * Determines which methods every
 * @Service class must implement.
 * <T> represents entity class
 * in a database.
 */
public interface ServiceInterface<T> {
    /// Nema Optional, vec sve metode vracaju null ako nesto nije dobro
    /// Metode vracaju DTO ili cele entitete ako treba
    /// Imena metoda da budu opisna i da pocinju sa get, create, update, delete
    /// Pomocne metode da budu private i obavezno da imaju dokumentaciju
    /// U servisu se kesiraju povratne vrednosti metoda

    /**
     * Get entity by its id
     * @param id of entity
     * @return entity or null
     */
    T getById(Long id);

    /**
     * Get all entities in the database table
     * @return all entities as DTOs or empty array
     */
    List<?> getAll();

    /**
     * Crete entity
     * @param dto represents a new entity
     * @return created entity or null
     */
    T create(DtoInterface dto);

    /**
     * Update an existing entity
     * @param dto represents an updated entity
     * @return updated entity or null
     */
    T update(DtoInterface dto);

    /**
     * Delete entity by its id
     * @param id of entity
     * @return operation success
     */
    boolean deleteById(Long id);

    /**
     * Delete all entities in the database table
     * Must have @Transactional
     * ADMIN only
     */
    void deleteAll();
}
