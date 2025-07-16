package movwe.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import movwe.domains.moderators.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Long> {

    @Query("SELECT m FROM Moderator m WHERE m.id = :id AND m.role <> 'ADMIN'")
    Optional<Moderator> findByIdCustom(Long id);

    Optional<Moderator> findByEmail(String email);

    @Query("SELECT m FROM Moderator m WHERE m.role <> 'ADMIN'")
    Optional<List<Moderator>> findAllCustom();

    @Modifying
    @Query("DELETE FROM Moderator m WHERE m.id = :id AND m.role <> 'ADMIN'")
    int deleteByIdCustom(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM Moderator e WHERE  e.role <> 'ADMIN'")
    void deleteAllCustom();
}
