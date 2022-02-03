package cn.zq.spshot.quartz.client.impl;

import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.common.entity.StatusCode;
import cn.zq.spshot.quartz.client.DataClient;
import org.springframework.stereotype.Component;

/**
 * @author 华韵流风
 * @ClassName DataClientImpl
 * @Date 2022/1/7 17:12
 * @packageName cn.zq.spshot.quartz.client.impl
 * @Description TODO
 */
@Component
public class DataClientImpl implements DataClient {
    @Override
    public Result getAllImages() {
        return new Result(StatusCode.FAIL, false, "该服务暂时无法使用，请稍后再试……");
    }
}
