package fx.github.greys.web.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class RoleVo implements Serializable {
    private static final long serialVersionUID = 3629709775180088587L;
    private Long id;
    private String name;
    private String desc;
    private int status;
    private long createTime;
    private long modifyTime;
    private List<PermissionVo> permissionVos;
}
