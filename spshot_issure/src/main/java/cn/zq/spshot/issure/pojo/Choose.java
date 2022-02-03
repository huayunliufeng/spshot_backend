package cn.zq.spshot.issure.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created on 2021/12/14
 *
 * @author 华韵流风
 */
@Data
@Document(collection = "spshot_choose")
public class Choose implements Serializable {
    @Id
    private String _id;

    private Integer userid;

    private Integer itemsid;

    private String choose;
}
