package cn.zq.spshot.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一的返回结果
 *
 * @author 华韵流风
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;
    private Boolean flag;
    private String message;
    private Object data;

    public Result(Integer code, Boolean flag, String message) {
        this.code = code;
        this.flag = flag;
        this.message = message;
    }

    public Result(Integer code, Boolean flag) {
        this.code = code;
        this.flag = flag;
    }
}
