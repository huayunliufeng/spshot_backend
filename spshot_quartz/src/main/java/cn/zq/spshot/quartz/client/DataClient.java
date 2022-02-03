package cn.zq.spshot.quartz.client;

import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.quartz.client.impl.DataClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 华韵流风
 * @ClassName DataClient
 * @Date 2022/1/7 17:11
 * @packageName cn.zq.spshot.quartz.client
 * @Description TODO
 */
@FeignClient(value = "spshot-data", fallback = DataClientImpl.class)
public interface DataClient {

    /**
     * 获取所有图片
     *
     * @return Result
     */
    @GetMapping("/promote/getAllImages")
    Result getAllImages();
}
