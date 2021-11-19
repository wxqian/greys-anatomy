package fx.github.greys.web.repository;

import fx.github.greys.web.entity.system.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    void deleteByRoleId(Long roleId);

    @Modifying
    @Transactional
    @Query("delete from RolePermission s where s.roleId in (?1)")
    void deleteAllByRoleIds(List<Long> ids);
}
