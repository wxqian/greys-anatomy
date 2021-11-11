package fx.github.greys.web.entity.system;

import java.io.Serializable;

public class RolePermission implements Serializable {
    private static final long serialVersionUID = 3170028135974122913L;

    private Long id;

    private long roleId;

    private long permissionId;
}
