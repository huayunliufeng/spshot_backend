package cn.zq.spshot.data.controller;

import cn.zq.spshot.common.aop.HasPermissions;
import cn.zq.spshot.common.entity.PageV;
import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.common.entity.StatusCode;
import cn.zq.spshot.data.pojo.FeedBack;
import cn.zq.spshot.data.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created on 2021/12/16
 *
 * @author 华韵流风
 */
@RestController
@RequestMapping("/feedback")
@RefreshScope
//@CrossOrigin
public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;


    /**
     * 新增反馈
     *
     * @param feedBack feedBack
     * @return Result
     */
    @PostMapping
    public Result add(@RequestBody FeedBack feedBack) {
        try {
            feedBackService.add(feedBack);
            return new Result(StatusCode.OK, true, "提交成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "提交失败");
    }

    /**
     * 根据id查询feedback
     *
     * @param id id
     * @return Result
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {

        try {
            return new Result(StatusCode.OK, true, "", feedBackService.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 根据id查询deal
     *
     * @param id id
     * @return Result
     */
    @GetMapping("/desc/deal/{id}")
    public Result findDealById(@PathVariable Integer id) {
        try {
            return new Result(StatusCode.OK, true, "", feedBackService.findDealById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 模糊查询
     *
     * @param pageV pageV
     * @param state state
     * @return Result
     */
    @HasPermissions(values = {"FEEDBACK_SELECT"})
    @PostMapping("/search/{state}")
    public Result findAllPage(@RequestBody PageV pageV, @PathVariable Integer state) {
        try {
            if (state != 1) {
                return new Result(StatusCode.OK, true, "", feedBackService.findAllFeedBack(pageV.getQueryString(), state, pageV.getCurrentPage(), pageV.getPageSize()));
            } else {
                return new Result(StatusCode.OK, true, "", feedBackService.findDealPage(pageV.getQueryString(), pageV.getCurrentPage(), pageV.getPageSize()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 查询未处理反馈数
     *
     * @return Result
     */
    @HasPermissions(values = {"FEEDBACK_SELECT"})
    @GetMapping("/count")
    public Result countNoDeal() {
        try {
            return new Result(StatusCode.OK, true, "", feedBackService.countNoDeal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 处理反馈
     *
     * @param map map
     * @return Result
     */
    @HasPermissions(values = {"DEAL_UPDATE"})
    @PostMapping("/deal")
    public Result dealFeedBack(@RequestBody Map<String, Object> map) {

        try {
            feedBackService.deal(map);
            return new Result(StatusCode.OK, true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "操作失败");
    }

    /**
     * 设置已读
     *
     * @param fbid fbid
     * @return Result
     */
    @GetMapping("/isread/{fbid}")
    public Result setIsRead(@PathVariable Integer fbid) {
        try {
            feedBackService.setIsRead(fbid);
            return new Result(StatusCode.OK, true, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "操作失败");
    }

    /**
     * 查询用户未读消息数
     *
     * @param userid userid
     * @return Result
     */
    @GetMapping("/deal/{userid}")
    public Result getDealIsNotRead(@PathVariable Integer userid) {
        try {
            return new Result(StatusCode.OK, true, "", feedBackService.getDealIsNotRead(userid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询用户未读消息数失败");
    }

    /**
     * 查询指定用户的消息
     *
     * @param userid userid
     * @return Result
     */
    @PostMapping("/search/user/{userid}")
    public Result findUserMessage(@PathVariable Integer userid, @RequestBody PageV pageV) {
        try {
            return new Result(StatusCode.OK, true, "", feedBackService.findUserMessage(userid, pageV.getCurrentPage(), pageV.getPageSize()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询系统消息失败");
    }


}
