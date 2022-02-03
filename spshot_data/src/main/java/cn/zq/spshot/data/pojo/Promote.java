package cn.zq.spshot.data.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Created on 2021/12/16
 *
 * @author 华韵流风
 */
@Data
public class Promote implements Serializable {

    @Id
    private Integer id;

    private String img;

    private String url;

    private String desc;

    private Date createtime;

    private Integer state;

    private String isdel;

}
