package cn.zq.spshot.issure.client.impl;

import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.common.entity.StatusCode;
import cn.zq.spshot.issure.client.QuartzClient;
import org.springframework.stereotype.Component;

/**
 * @author 华韵流风
 * @ClassName QuartzClientImpl
 * @Date 2022/1/14 20:45
 * @packageName cn.zq.spshot.issure.client.impl
 * @Description TODO
 */
@Component
public class QuartzClientImpl implements QuartzClient {

    @Override
    public Result setState(Integer id, Integer effecttime) {
        return new Result(StatusCode.FAIL,false,"此服务暂时无法调用，请稍后再试……");
    }
}
