package movwe.utils.interfaces;

import java.io.Serializable;

public interface DtoInterface extends Serializable {
    /// Svaki DTO implementira ovu klasu
    /// Svaki entitet mora da ima bar 3 DTO-a:
    /// 1. Dto koji koristimo za get
    /// 2. Dto koji koristimo za create
    /// 3. Dto koji koristimo za update
}
