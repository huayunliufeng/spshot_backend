package cn.zq.spshot.quartz.controller;

import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.common.entity.StatusCode;
import cn.zq.spshot.common.utils.WeiXinUtil;
import cn.zq.spshot.quartz.client.IssureClient;
import cn.zq.spshot.quartz.job.SetIssureState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author 华韵流风
 * @ClassName QuartzController
 * @Date 2022/1/7 20:04
 * @packageName cn.zq.spshot.quartz.controller
 * @Description TODO
 */
@RestController
//@CrossOrigin
@RefreshScope
@RequestMapping("/quartz")
public class QuartzController {

    @Resource
    private IssureClient issureClient;

    @Autowired
    private WeiXinUtil weiXinUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 设置过期
     *
     * @param id         id
     * @param effecttime effecttime
     */
    @GetMapping("/{id}/{effecttime}")
    public Result setState(@PathVariable Integer id, @PathVariable Integer effecttime) {
        try {
            //设置过期
            ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
            SetIssureState setIssureState = new SetIssureState(issureClient, weiXinUtil, redisTemplate, id);
            ScheduledFuture<Map<String, Map<String, Object>>> schedule = pool.schedule(setIssureState, effecttime, TimeUnit.MINUTES);
            System.out.println(new Date() + "," + schedule.get());
            schedule.cancel(false);
            pool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        }
        return new Result(StatusCode.OK,true,"");
    }


}
