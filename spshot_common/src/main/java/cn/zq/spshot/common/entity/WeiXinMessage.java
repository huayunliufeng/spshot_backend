package cn.zq.spshot.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author 华韵流风
 * @ClassName WeiXinMessage
 * @Date 2022/1/9 16:50
 * @packageName cn.zq.spshot.common.entity
 * @Description 微信消息类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeiXinMessage {

    /**
     * 接口调用凭证
     */
    private String access_token;

    /**
     * 接收者（用户）的 openid
     */
    private String touser;

    /**
     * 所需下发的订阅模板id
     */
    private String template_id;

    /**
     * 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
     */
    private String page;

    /**
     * 模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }
     */
    private Map<String, Object> data;


}
