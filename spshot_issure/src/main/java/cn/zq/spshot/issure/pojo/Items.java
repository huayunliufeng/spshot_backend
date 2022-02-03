package cn.zq.spshot.issure.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 2021/12/13
 *
 * @author 华韵流风
 */
@Data
public class Items implements Serializable {

    @Id
    private Integer id;

    private String question;

    private Integer optional;

    private String isdel;

    private List<Item> item;

}
