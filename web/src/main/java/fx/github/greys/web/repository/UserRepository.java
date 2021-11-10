package fx.github.greys.web.repository;

import fx.github.greys.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
