package movwe.utils.interfaces;

import org.springframework.http.ResponseEntity;

public interface ControllerInterface {
    ResponseEntity<?> create(DtoInterface dto);

}
