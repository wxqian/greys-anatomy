package fx.github.greys.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class TreePermissionVo implements Serializable {
    private static final long serialVersionUID = -4572968509422008792L;

    private Long id;

    private String value;

    @JsonProperty("pId")
    private String pId;

    private String title;

}
