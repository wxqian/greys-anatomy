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

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "create_time")
    private long createTime = System.currentTimeMillis();

    @Column(name = "modify_time")
    private long modifyTime = System.currentTimeMillis();

}
