package cn.zq.spshot.user.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;


/**
 * @author 华韵流风
 * @Date 2021-11-27 21:49:10
 */
@Data
public class User implements Serializable {

    @Id
    private Integer id;

    private String openid;

    private String username;

    private String avatarUrl;

    private Date registime;

    private Integer todaynum;

    private Integer totalissure;

    private Integer totalpart;

    private int state;

    private String isdel;

}
