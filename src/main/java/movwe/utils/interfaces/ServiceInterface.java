package movwe.utils.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Represents an interface that should
 * be implemented by every @Service
 * class.
 * Contains methods for CRUD operations.
 */
public interface ServiceInterface {
    /**
     * Get one entity by its id
     * @param id of entity
     * @return T as Object
     */
    Optional<DtoInterface> get(Long id);

    /**
     * Get all entities in the database table
     * @return T as List of Objects
     */
    List<?> getAll();

    /**
     * Insert entity into database table
     * @param dto new entity as dto
     * @return new entity
     */
    DtoInterface add(DtoInterface dto);

    /**
     * Update one entity
     * @param id of entity in the database table
     * @param dto with new values
     * @return updated entity
     */
    DtoInterface update(Long id, DtoInterface dto);

    /**
     * Delete one entity in a table
     * @param id of entity
     */
    void delete(Long id);

    /**
     * Empty the whole database table
     */
    void deleteAll();
}
