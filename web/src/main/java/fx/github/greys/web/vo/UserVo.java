package fx.github.greys.web.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserVo implements Serializable {
    private static final long serialVersionUID = -7749825120066886561L;

    private Long id;

    private String username;

    private String token;
}
