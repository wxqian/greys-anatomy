package fx.github.greys.web.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = -7749825120066886561L;

    private Long id;

    private String username;


}
