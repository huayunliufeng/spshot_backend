package cn.zq.spshot.data.client;

import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.data.client.impl.IssureClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 华韵流风
 * @ClassName IssureClient
 * @Date 2021/12/19 13:23
 * @packageName cn.zq.spshot.data.client
 * @Description TODO
 */
@FeignClient(value = "spshot-issure", fallback = IssureClientImpl.class)
public interface IssureClient {

    /**
     * 查询热门投票
     *
     * @return Result
     */
    @GetMapping("/issure/hotissure")
    Result getHotIssure();


}
