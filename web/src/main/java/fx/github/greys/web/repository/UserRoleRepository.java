package fx.github.greys.web.repository;

import fx.github.greys.web.entity.system.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    void deleteByUserId(Long id);
}
