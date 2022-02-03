package cn.zq.spshot.data.controller;

import cn.zq.spshot.common.aop.HasPermissions;
import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.common.entity.StatusCode;
import cn.zq.spshot.data.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

/**
 * @author 华韵流风
 * @ClassName ChartController
 * @Date 2021/12/19 16:34
 * @packageName cn.zq.spshot.data.controller
 * @Description TODO
 */
@RestController
@RefreshScope
//@CrossOrigin
@RequestMapping("/chart")
public class ChartController {

    @Autowired
    private ChartService chartService;


    /**
     * 查询月份
     *
     * @param currYear currYear
     * @return Result
     */
    @HasPermissions(values = {"IS_LOGIN"})
    @GetMapping("/xArray/{currYear}")
    public Result getxArray(@PathVariable Integer currYear) {
        try {
            int[] xArray;
            if (currYear == 2021) {
                xArray = new int[]{11, 12};
            } else if (currYear == Calendar.getInstance().get(Calendar.YEAR)) {
                int maxMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
                xArray = new int[maxMonth];
                for (int i = 0; i < maxMonth; i++) {
                    xArray[i] = i + 1;
                }
            } else {
                xArray = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
            }
            return new Result(StatusCode.OK, true, "", xArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询月份失败");
    }

    /**
     * 查询用户数据
     *
     * @param currYear currYear
     * @param xArray   xArray
     * @return Result
     */
    @HasPermissions(values = {"IS_LOGIN"})
    @GetMapping("/userData/{currYear}/{xArray}")
    public Result getUserData(@PathVariable Integer currYear, @PathVariable int[] xArray) {
        try {

            return new Result(StatusCode.OK, true, "", chartService.getUserData(currYear, xArray));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询用户数据失败");
    }

    /**
     * 查询用户反馈数据
     *
     * @param currYear currYear
     * @param xArray   xArray
     * @return Result
     */
    @HasPermissions(values = {"IS_LOGIN"})
    @GetMapping("/feedbackData/{currYear}/{xArray}")
    public Result getFeedbackData(@PathVariable Integer currYear, @PathVariable int[] xArray) {
        try {

            return new Result(StatusCode.OK, true, "", chartService.getFeedbackData(currYear, xArray));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询用户反馈数据失败");
    }

    /**
     * 查询发布投票数据
     *
     * @param currYear currYear
     * @param xArray   xArray
     * @return Result
     */
    @HasPermissions(values = {"IS_LOGIN"})
    @GetMapping("/issureData/{currYear}/{xArray}")
    public Result getIssureData(@PathVariable Integer currYear, @PathVariable int[] xArray) {
        try {
            return new Result(StatusCode.OK, true, "", chartService.getIssureData(currYear, xArray));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询发布投票数据失败");
    }

    /**
     * 查询参与投票数据
     *
     * @param currYear currYear
     * @param xArray   xArray
     * @return Result
     */
    @HasPermissions(values = {"IS_LOGIN"})
    @GetMapping("/partData/{currYear}/{xArray}")
    public Result getPartData(@PathVariable Integer currYear, @PathVariable int[] xArray) {
        try {
            return new Result(StatusCode.OK, true, "", chartService.getPartData(currYear, xArray));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询参与投票数据失败");
    }

}
