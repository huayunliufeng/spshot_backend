package cn.zq.spshot.common.utils;

import cn.zq.spshot.common.entity.WeiXinMessage;
import com.alibaba.fastjson.JSON;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 华韵流风
 * @ClassName WeiXinUtil
 * @Date 2022/1/9 15:51
 * @packageName cn.zq.spshot.common.utils
 * @Description TODO
 */
@Component
@ConfigurationProperties(prefix = "weixin.config")
@EnableConfigurationProperties
public class WeiXinUtil {

    private String appid;

    private String secret;

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * 获取openid
     *
     * @param code code
     * @return Map
     */
    public Map getOpenId(String code) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={code}&grant_type=authorization_code", String.class, appid, secret, code);
        System.out.println(responseEntity.getBody());
        return JSON.parseObject(responseEntity.getBody(), Map.class);
    }

    /**
     * 获取用户手机号
     *
     * @param code         code
     * @param access_token access_token
     * @return String
     */
    public Map getPhoneNumber(String access_token, String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("access_token", access_token);
        map.put("code", code);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + access_token, map, String.class);
        System.out.println(responseEntity.getBody());
        return JSON.parseObject(responseEntity.getBody(), Map.class);
    }


    /**
     * 获取access_token
     *
     * @return String
     */
    public Map getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}", String.class, appid, secret);
        System.out.println(responseEntity.getBody());
        return JSON.parseObject(responseEntity.getBody(), Map.class);
    }

    /**
     * 发送消息
     *
     * @param weiXinMessage weiXinMessage
     * @return Map
     */
    public Map pushMessage(WeiXinMessage weiXinMessage) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + weiXinMessage.getAccess_token();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, weiXinMessage, String.class);
        System.out.println(responseEntity.getBody());
        return JSON.parseObject(responseEntity.getBody(), Map.class);
    }
}
