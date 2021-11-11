package fx.github.greys.web.dto;

import fx.github.greys.web.entity.system.Permission;
import fx.github.greys.web.entity.system.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = -7749825120066886561L;

    private Long id;

    private String username;

    private List<Role> roles;

    private List<Permission> permissions;
}
