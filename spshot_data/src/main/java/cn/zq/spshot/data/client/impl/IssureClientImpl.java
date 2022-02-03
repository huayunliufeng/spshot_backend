package cn.zq.spshot.data.client.impl;

import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.common.entity.StatusCode;
import cn.zq.spshot.data.client.IssureClient;
import org.springframework.stereotype.Component;

/**
 * @author 华韵流风
 * @ClassName IssureClientImpl
 * @Date 2021/12/19 13:24
 * @packageName cn.zq.spshot.data.client.impl
 * @Description TODO
 */
@Component
public class IssureClientImpl implements IssureClient {

    @Override
    public Result getHotIssure() {
        return new Result(StatusCode.FAIL, false, "该服务暂时无法使用，请稍后再试……");
    }
}
