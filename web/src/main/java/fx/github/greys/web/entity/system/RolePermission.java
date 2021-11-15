package fx.github.greys.web.entity.system;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString
@Table(name = "t_role_permission")
@Entity
public class RolePermission implements Serializable {
    private static final long serialVersionUID = 3170028135974122913L;

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    //角色id
    @Column(name = "role_id")
    private long roleId;

    //权限id
    @Column(name = "permission_id")
    private long permissionId;
}
