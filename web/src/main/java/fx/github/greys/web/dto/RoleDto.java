package fx.github.greys.web.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class RoleDto implements Serializable {
    private static final long serialVersionUID = -6702974300874291509L;

    private String name;

    private String desc;

    private String permissions;
}
