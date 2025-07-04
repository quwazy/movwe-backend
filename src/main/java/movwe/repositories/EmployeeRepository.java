package movwe.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import movwe.domains.employees.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Modifying
    @Query("DELETE FROM Employee e WHERE e.id = :id")
    int deleteByIdCustom(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM Employee e WHERE e.email = :email AND e.role <> 'ADMIN'")
    int deleteByEmail(@Param("email") String email);

    @Modifying
    @Query("DELETE FROM Employee e WHERE  e.role <> 'ADMIN'")
    void deleteAll();
}
