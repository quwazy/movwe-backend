package movwe.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import movwe.domains.clients.entities.Client;
import movwe.domains.movies.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

   Optional<List<Movie>> findAllByClient(Client client);

   int deleteAllByClient_Email(String clientEmail);

   @Modifying
   @Query("DELETE FROM Movie m WHERE m.id = :id")
   int deleteByIdCustom(@Param("id") Long id);
}
