package fx.github.greys.web.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class SessionUser implements Serializable {

    private long id;

    private String username;

}
