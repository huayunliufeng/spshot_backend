package cn.zq.spshot.issure.client;

import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.issure.client.impl.QuartzClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 华韵流风
 * @ClassName QuartzClient
 * @Date 2022/1/14 20:44
 * @packageName cn.zq.spshot.issure.client
 * @Description TODO
 */
@FeignClient(value = "spshot-quartz", fallback = QuartzClientImpl.class)
public interface QuartzClient {

    /**
     * 设置过期
     *
     * @param id         id
     * @param effecttime effecttime
     */
    @GetMapping("/quartz/{id}/{effecttime}")
    Result setState(@PathVariable Integer id, @PathVariable Integer effecttime);
}
