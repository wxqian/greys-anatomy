package fx.github.greys.web.entity.system;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Role implements Serializable {

    private static final long serialVersionUID = 6017975227701397932L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "desc", length = 255)
    private String desc;

    //状态 0 已删除 1 使用中
    @Column(name = "status", columnDefinition = "int default 1")
    private int status;

    @Column
    private long createTime = System.currentTimeMillis();

    @Column
    private long modifyTime = System.currentTimeMillis();
}
