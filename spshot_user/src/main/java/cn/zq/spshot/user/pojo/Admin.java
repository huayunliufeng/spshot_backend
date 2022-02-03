package cn.zq.spshot.user.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Set;

/**
 * @author 华韵流风
 * @Date 2021-11-27 21:49:10
 */
@Data
public class Admin implements Serializable {

    @Id
    private Integer id;

    private String loginname;

    private String password;

    private Integer state;

    private String isdel;

    private Set<Role> roles;

}
