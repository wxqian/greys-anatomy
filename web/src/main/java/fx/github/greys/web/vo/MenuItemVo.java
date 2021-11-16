package fx.github.greys.web.vo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class MenuItemVo implements Serializable {

    private static final long serialVersionUID = 3587132396472162681L;

    private String icon;
    private Long id;
    private String name;
    private Long parentId;
    private int sort;
    private String url;

    private String viewPath;
}
