package nl.markheeling.quizly.repository;

import nl.markheeling.quizly.model.Role;
import nl.markheeling.quizly.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
