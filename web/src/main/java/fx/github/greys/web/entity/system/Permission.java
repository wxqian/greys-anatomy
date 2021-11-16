package fx.github.greys.web.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@ToString
@Table(name = "t_permission", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Entity
public class Permission implements Serializable {

    private static final long serialVersionUID = -7824790123587641670L;

    //id主键 自增
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    //名称
    @Column(length = 50)
    private String name;

    //描述
    @Column
    private String desc;

    //路由
    @Column
    private String url;

    //图标
    @Column(length = 20)
    private String icon;

    //父权限
    @Column
    private Long parent;

    //0 已删除 1 使用中
    @Column(columnDefinition = "int default 1")
    private int status;

    //排序
    @Column(columnDefinition = "int default 0")
    private int sorts;

    //创建时间
    @Column(name = "create_time")
    private long createTime = System.currentTimeMillis();

    //更新时间
    @Column(name = "modify_time")
    private long modifyTime = System.currentTimeMillis();

    @Column(name = "view_path")
    private String viewPath;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "permissions")
    @JsonIgnoreProperties({"roles"})
    private List<Role> roles;

}
