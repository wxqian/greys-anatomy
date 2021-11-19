package fx.github.greys.web.repository;

import fx.github.greys.web.entity.system.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    void deleteByUserId(Long id);

    @Modifying
    @Transactional
    @Query("delete from UserRole s where s.roleId in (?1)")
    void deleteAllByRoleIds(List<Long> ids);
}
