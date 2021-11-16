package fx.github.greys.web.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class PermissionVo implements Serializable {
    private static final long serialVersionUID = -7846184943023735534L;

    private Long id;
    private String name;
    private String desc;
    private String url;
    private String icon;
    private Long parentId;
    private int status;
    private int sorts;
    private long createTime;
    private long modifyTime;
}
