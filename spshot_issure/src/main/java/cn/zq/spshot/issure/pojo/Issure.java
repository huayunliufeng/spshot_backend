package cn.zq.spshot.issure.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created on 2021/12/13
 *
 * @author 华韵流风
 */
@Data
public class Issure implements Serializable {

    @Id
    private Integer id;

    private Integer userid;

    private String username;

    private String title;

    private Date createtime;

    private Date accepttime;

    private Integer effecttime;

    private Integer maxperson;

    private Integer currperson;

    private String partcode;

    private String partpass;

    private String ispublic;

    private String isanony;

    private Integer adminid;

    private Integer state;

    private String isexpire;

    private String isdel;

    private List<Items> items;
}
