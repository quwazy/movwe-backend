package movwe.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import movwe.domains.clients.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    Optional<Client> findByUsername(String username);

    @Modifying
    @Query("DELETE FROM Client c WHERE c.id = :id")
    int deleteByIdCustom(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM Client c WHERE c.email = :email")
    int deleteByEmail(@Param("email") String email);
}
