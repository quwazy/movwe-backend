package movwe.utils.interfaces;

import java.util.List;

public interface ServiceInterface<T> {
    /**
     * Get one entity by its id
     * @param id of entity
     * @return T as Object
     */
    T get(Long id);

    /**
     * Get all entities in database table
     * @return T as List of Objects
     */
    List<T> getAll();

    /**
     * Insert entity into database table
     * @param dto new entity as dto
     * @return if operation was successful
     */
    boolean add(DtoInterface dto);

    /**
     * Update one entity
     * @param id of entity in database table
     * @param dto with new values
     * @return if opearation was successful
     */
    boolean update(Long id, DtoInterface dto);

    /**
     * Delete one entity in a table
     * @param id of entity
     * @return if operation was successful
     */
    boolean delete(Long id);

    /**
     * Empty whole table
     * @return if operation was successful
     */
    boolean deleteAll();
}
