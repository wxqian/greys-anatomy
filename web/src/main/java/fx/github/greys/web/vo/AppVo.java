package fx.github.greys.web.vo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class AppVo implements Serializable {
    private static final long serialVersionUID = 4221929421795781874L;

    private Integer id;

    private String ip;

    private int port;

    private int status;
}
