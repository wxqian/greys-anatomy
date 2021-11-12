package fx.github.greys.web.entity.system;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_user_role")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 2811475591777132299L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "role_id")
    private long roleId;
}
