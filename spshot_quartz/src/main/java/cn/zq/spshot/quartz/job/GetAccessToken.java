package cn.zq.spshot.quartz.job;

import cn.zq.spshot.common.utils.WeiXinUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author 华韵流风
 * @ClassName GetAccessToken
 * @Date 2022/1/9 16:32
 * @packageName cn.zq.spshot.quartz.job
 * @Description TODO
 */
@Component
public class GetAccessToken extends QuartzJobBean {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private WeiXinUtil weiXinUtil;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            Map<String, Object> accessToken = weiXinUtil.getAccessToken();
            String access_token = (String) accessToken.get("access_token");
            System.out.println(accessToken.get("errcode"));
            System.out.println(accessToken.get("errmsg"));
            System.out.println(accessToken.get("expires_in"));
            redisTemplate.opsForValue().set("access_token", access_token);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        }

    }

}
