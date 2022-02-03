package cn.zq.spshot.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 华韵流风
 * @ClassName PageV
 * @Date 2021/11/27 20:38
 * @packageName cn.zq.spshot.common.entity
 * @Description 分页类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageV {
    private int currentPage;
    private int pageSize;
    private int total;
    private String queryString;
}
