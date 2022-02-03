package cn.zq.spshot.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 华韵流风
 * @ClassName TemplateData
 * @Date 2022/1/9 18:03
 * @packageName cn.zq.spshot.common.entity
 * @Description TODO
 */
public class TemplateData {

    private String value;

    public TemplateData(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
