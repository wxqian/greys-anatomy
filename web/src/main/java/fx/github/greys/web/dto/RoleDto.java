package fx.github.greys.web.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class RoleDto implements Serializable {
    private static final long serialVersionUID = -6702974300874291509L;

    //角色id
    private Long id;

    //角色名称
    private String name;

    //描述
    private String desc;

    //权限列表，多个用,隔开
    private String permissions;
}
