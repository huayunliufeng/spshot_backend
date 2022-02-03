package cn.zq.spshot.user.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Set;

/**
 * Created on 2021/12/20
 *
 * @author 华韵流风
 */
@Data
public class Menu implements Serializable {

    @Id
    private Integer id;

    private String path;

    private String title;

    private String icon;

    private Set<Menu> children;

}
