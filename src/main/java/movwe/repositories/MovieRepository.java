package movwe.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import movwe.domains.movies.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

   Optional<List<Movie>> findAllByUser_IdOrderByCreationDateDesc(Long userId);

   Optional<List<Movie>> findAllByUser_UsernameOrderByCreationDateDesc(String username);

   @Modifying
   @Query("DELETE FROM Movie m WHERE m.id = :id")
   int deleteByIdCustom(@Param("id") Long id);

   @Modifying
   @Query("DELETE FROM Movie m WHERE m.id = :id AND m.user.username = :username")
   int deleteByIdAndUser_Username(@Param("id") Long id, @Param("username") String username);

   @Modifying
   int deleteAllByUser_Id(Long userId);
}
