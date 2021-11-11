package fx.github.greys.web.entity.system;

import java.io.Serializable;

public class Dict implements Serializable {
    private static final long serialVersionUID = 8549120255992565099L;

    private Long id;

    private String label;

    private Long parentId;

    private String desc;

    private long createTime;

    private long modifyTime;
}
