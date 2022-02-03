package cn.zq.spshot.quartz.job;

import cn.zq.spshot.common.entity.TemplateData;
import cn.zq.spshot.common.entity.WeiXinMessage;
import cn.zq.spshot.common.utils.WeiXinUtil;
import cn.zq.spshot.quartz.client.IssureClient;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * @author 华韵流风
 * @ClassName SetIssureState
 * @Date 2022/1/7 20:50
 * @packageName cn.zq.spshot.quartz.job
 * @Description TODO
 */
public class SetIssureState implements Callable<Map<String, Map<String, Object>>> {

    private final IssureClient issureClient;

    private final WeiXinUtil weiXinUtil;

    private final RedisTemplate<String, String> redisTemplate;

    private final Integer id;

    public SetIssureState(IssureClient issureClient, WeiXinUtil weiXinUtil, RedisTemplate<String, String> redisTemplate, Integer id) {
        this.issureClient = issureClient;
        this.weiXinUtil = weiXinUtil;
        this.redisTemplate = redisTemplate;
        this.id = id;
    }


    @Override
    public Map<String, Map<String, Object>> call() throws Exception {
        try {
            //如果已经达到最大人数
            String isContinue = redisTemplate.opsForValue().get("issureMaxPerson_" + id);
            if ("1".equals(isContinue)) {
                return null;
            }
            //设置过期
            issureClient.setIssureExpire(id);
            // 给客户端发送消息
            // 获取openid
            List<String> openIds = (List<String>) issureClient.getOpenId(id).getData();
            //发送消息
            String access_token = redisTemplate.opsForValue().get("access_token");
            Map<String, Object> map = new HashMap<>();
            map.put("thing1", new TemplateData((String) ((Map<String, Object>) (issureClient.getIssureById(id).getData())).get("title")));
            map.put("phrase2", new TemplateData("点击查看"));
            map.put("time3", new TemplateData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance(TimeZone.getTimeZone("CCT")).getTime())));
            Map<String, Map<String, Object>> res = new HashMap<>();
            for (String openId : openIds) {
                WeiXinMessage weiXinMessage = new WeiXinMessage(access_token, openId, "", "" + id, map);
//                Map<String, Object> map1 = (Map<String, Object>) weiXinUtil.pushMessage(weiXinMessage);
//                res.put(openId, map1);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        }
        return null;
    }
}
