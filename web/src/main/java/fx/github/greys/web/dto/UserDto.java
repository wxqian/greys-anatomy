package fx.github.greys.web.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserDto implements Serializable {
    private static final long serialVersionUID = -1467701679896113914L;

    //id
    private Long id;

    //用户名
    private String username;

    //密码
    private String password;

    //角色集合，多个用,隔开
    private String roles;
}
