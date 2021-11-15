package fx.github.greys.web.entity.system;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "t_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 7400800432633383363L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    //用户名
    @Column(name = "username", length = 100)
    private String username;

    //密码
    @Column(name = "password", length = 100)
    private String password;

    //创建时间
    @Column(name = "create_time")
    private long createTime = System.currentTimeMillis();

    //更新时间
    @Column(name = "modify_time")
    private long modifyTime = System.currentTimeMillis();

}
