package fx.github.greys.web.entity.system;

import java.io.Serializable;

public class Permission implements Serializable {

    private static final long serialVersionUID = -7824790123587641670L;
    private Long id;

    private String name;

    private String desc;

    private int status;

    private long createTime;

    private long modifyTime;
}
