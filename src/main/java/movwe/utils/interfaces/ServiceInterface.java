package movwe.utils.interfaces;

import java.util.List;

public interface ServiceInterface<T> {
    T get(Long id);
    List<T> getAll();
    boolean add(DtoInterface dto);  //dto
    boolean edit(Long id, DtoInterface dto); //dto
    boolean delete(Long id);
    boolean deleteAll();
}
