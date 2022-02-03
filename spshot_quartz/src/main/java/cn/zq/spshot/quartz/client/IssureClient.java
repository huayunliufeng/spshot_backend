package cn.zq.spshot.quartz.client;

import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.quartz.client.impl.IssureClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 华韵流风
 * @ClassName IssureClient
 * @Date 2022/1/7 18:31
 * @packageName cn.zq.spshot.quartz.client
 * @Description TODO
 */
@FeignClient(value = "spshot-issure", fallback = IssureClientImpl.class)
public interface IssureClient {

    /**
     * 重置每日发布数
     *
     * @return Result
     */
    @GetMapping("/issure/recTodayNum")
    Result recTodayNum();

    /**
     * 设置过期
     *
     * @param id id
     * @return Result
     */
    @GetMapping("/issure/setIssureExpire/{id}")
    Result setIssureExpire(@PathVariable Integer id);

    /**
     * 根据issureid获取用户openid
     *
     * @param issureid issureid
     * @return Result
     */
    @GetMapping("/issure/getOpenId/{issureid}")
    Result getOpenId(@PathVariable Integer issureid);

    /**
     * 根据id查询issure
     *
     * @param issureid issureid
     * @return Result
     */
    @GetMapping("/issure/{issureid}")
    Result getIssureById(@PathVariable Integer issureid);
}
