package cn.zq.spshot.user.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Created on 2021/12/10
 *
 * @author 华韵流风
 */
@Data
public class Permission {
    @Id
    private Integer id;

    private String name;

    private String keyword;

    /**
     * 使用hibernate不可使用mysql关键字作为列名例如desc等
     */
    private String descr;

    private Integer state;

    private String isdel;

}
