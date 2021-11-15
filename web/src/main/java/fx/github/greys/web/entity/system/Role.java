package fx.github.greys.web.entity.system;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "t_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Role implements Serializable {

    private static final long serialVersionUID = 6017975227701397932L;
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    //角色名称
    @Column(name = "name", length = 50)
    private String name;

    //描述
    @Column(name = "desc")
    private String desc;

    //状态 0 已删除 1 使用中
    @Column(name = "status", columnDefinition = "int default 1")
    private int status = 1;

    //创建时间
    @Column(name = "create_time")
    private long createTime = System.currentTimeMillis();

    //更新时间
    @Column(name = "modify_time")
    private long modifyTime = System.currentTimeMillis();

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "t_user_role",joinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"))
    private List<User> user;
}
