package fx.github.greys.web.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class PermissionDto implements Serializable {
    private static final long serialVersionUID = 1248679303627851608L;

    //权限id
    private Long id;
    //权限名称
    private String name;
    //权限描述
    private String desc;
    //url
    private String url;
    //icon
    private String icon;
    //父权限id
    private Long parentId;
    //状态
    private int status = 1;
    //排序
    private int sorts = 0;

    private String viewPath;
}
