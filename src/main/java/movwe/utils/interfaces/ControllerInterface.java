package movwe.utils.interfaces;

import org.springframework.http.ResponseEntity;

/**
 * Represents an interface that should
 * be implemented by every @RestController
 * class.
 * Contains methods for CRUD operations.
 */
public interface ControllerInterface {
    /**
     * Get one entity bt its id
     * @param id of entity
     * @return Entity as JSON object or BadRequest
     */
    ResponseEntity<?> get(Long id);

    /**
     * Get all entities in the database table
     * @return All entities as JSON objects
     */
    ResponseEntity<?> getAll();

    /**
     * Add new entity
     * @param dto New entity as JSON
     * @return 200 OK if saving was successful
     */
    ResponseEntity<?> add(DtoInterface dto);

    /**
     * Update entity by its id
     * @param id of entity
     * @param dto new entity as dto
     * @return 200 OK if updating was successful
     */
    ResponseEntity<?> update(Long id, DtoInterface dto);

    /**
     * Delete one entity in a database by its id
     * @param id of entity
     * @return 200 OK if deleting was successful
     */
    ResponseEntity<?> delete(Long id);

    /**
     * Empty database table
     * Could be used only by Role = ADMIN users
     * @return 200 OK if deleting was successful
     */
    ResponseEntity<?> deleteAll();
}
