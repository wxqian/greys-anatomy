package fx.github.greys.web.repository;

import fx.github.greys.web.entity.system.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}
