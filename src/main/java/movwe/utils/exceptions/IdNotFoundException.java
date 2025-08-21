package movwe.utils.exceptions;

public class IdNotFoundException extends RuntimeException {

    public IdNotFoundException(String type, Long id) {
        super(type + " with id: " + id + " not found!");
    }
}
