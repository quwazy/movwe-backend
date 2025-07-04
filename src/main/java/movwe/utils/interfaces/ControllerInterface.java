package movwe.utils.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Determines which methods every
 * @Controller class must implement.
 */
public interface ControllerInterface {
    /// Sve metode vracaju ResponseEntity<?>
    /// U sebi imaju try/cache blok i u cache
    /// bloku vracaju Bad Request sa porukom iz Exceptiona
    /// Controller klase pakuju entitete u dto-ove
    /// Imena metoda da budu opisna i da pocinju sa get, create, update, delete
    /// Operation anotacija da ide na vrh, a Mapping da ide odmah iznad metode

    @Operation(summary = "Get entity by id")
    @GetMapping(path = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getById(@PathVariable Long id);

    @Operation(summary = "Get entity by email")
    @GetMapping(path = "/getByEmail/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getByEmail(@PathVariable String email);

    @Operation(summary = "Get all entities")
    @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getAll();

    @Operation(summary = "Create new entity")
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> create(@RequestBody DtoInterface dto);

    @Operation(summary = "Update entity")
    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> update(@RequestBody DtoInterface dto);

    @Operation(summary = "Delete entity by id")
    @DeleteMapping(path = "/deleteById/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id);

    @Operation(summary = "Delete entity by email")
    @DeleteMapping(path = "/deleteByEmail/{email}")
    ResponseEntity<?> deleteByEmail(@PathVariable String email);

    @Operation(summary = "Delete all entities")
    @DeleteMapping(path = "/deleteAll")
    @PreAuthorize( "hasRole('ADMIN')")
    ResponseEntity<?> deleteAll();
}
