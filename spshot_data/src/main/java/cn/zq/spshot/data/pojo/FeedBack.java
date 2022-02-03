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
public class FeedBack implements Serializable {

    @Id
    private Integer id;

    private Integer userid;

    private String username;

    private Date createtime;

    private String title;

    private String details;

    private Integer state;

    private String isdel;

}
