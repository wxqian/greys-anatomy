package fx.github.greys.web.repository;

import fx.github.greys.web.entity.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
