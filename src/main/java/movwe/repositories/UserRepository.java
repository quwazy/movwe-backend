package movwe.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import movwe.domains.users.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    @EntityGraph(attributePaths = {"friendList"})
    Optional<User> findByUsernameAndFetchFriendList(String username);

    @Query("SELECT u FROM User u WHERE u.username LIKE :prefix AND u.username != :excludeUsername")
    Optional<List<User>> findUsersByUsernameStartingWith(@Param("prefix") String prefix, @Param("excludeUsername") String excludeUsername, Pageable pageable);

    @Modifying
    @Query("DELETE FROM User c WHERE c.id = :id")
    int deleteByIdCustom(@Param("id") Long id);
}
