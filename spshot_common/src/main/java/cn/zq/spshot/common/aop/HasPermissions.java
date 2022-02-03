package cn.zq.spshot.common.aop;

import java.lang.annotation.*;

/**
 * @author 华韵流风
 * @ClassName HasPermissions
 * @Date 2022/1/16 18:21
 * @packageName cn.zq.spshot.common.aop
 * @Description TODO
 */
//注解生效的位置，此处表示在方法上
@Target(ElementType.METHOD)
//注解生效时期
@Retention(RetentionPolicy.RUNTIME)
//生成文档
@Documented
public @interface HasPermissions {
    String[] values();
}
