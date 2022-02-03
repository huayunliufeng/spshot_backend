package cn.zq.spshot.quartz.client.impl;

import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.common.entity.StatusCode;
import cn.zq.spshot.quartz.client.IssureClient;
import org.springframework.stereotype.Component;

/**
 * @author 华韵流风
 * @ClassName IssureClientImpl
 * @Date 2022/1/7 18:31
 * @packageName cn.zq.spshot.quartz.client.impl
 * @Description TODO
 */
@Component
public class IssureClientImpl implements IssureClient {
    @Override
    public Result recTodayNum() {
        return new Result(StatusCode.FAIL, false, "该服务暂时无法使用，请稍后再试……");
    }

    @Override
    public Result getOpenId(Integer issureid) {
        return new Result(StatusCode.FAIL, false, "该服务暂时无法使用，请稍后再试……");
    }

    @Override
    public Result setIssureExpire(Integer id) {
        return new Result(StatusCode.FAIL, false, "该服务暂时无法使用，请稍后再试……");
    }

    @Override
    public Result getIssureById(Integer issureid) {
        return new Result(StatusCode.FAIL, false, "该服务暂时无法使用，请稍后再试……");
    }
}
