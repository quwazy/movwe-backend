package movwe.utils.interfaces;

import java.util.List;

public interface ServiceInterface<T> {
    /**
     * Get one entity by its id
     * @param id of entity
     * @return T as Object
     */
    DtoInterface get(Long id);

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
    T add(DtoInterface dto);

    /**
     * Update one entity
     * @param id of entity in the database table
     * @param dto with new values
     * @return updated entity
     */
    T update(Long id, DtoInterface dto);

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
