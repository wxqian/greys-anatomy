package fx.github.greys.web.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserDto implements Serializable {
    private static final long serialVersionUID = -1467701679896113914L;

    private String username;

    private String password;

    private String roles;
}
