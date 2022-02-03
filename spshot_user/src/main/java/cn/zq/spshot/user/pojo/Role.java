package cn.zq.spshot.user.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Set;

/**
 * Created on 2021/12/9
 *
 * @author 华韵流风
 */
@Data
public class Role implements Serializable {

    @Id
    private Integer id;

    private String name;

    private String keyword;

    private String descr;

    private Integer state;

    private String isdel;

    private Set<Permission> permissions;

}
