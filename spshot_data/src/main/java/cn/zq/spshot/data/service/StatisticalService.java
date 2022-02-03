package cn.zq.spshot.data.service;

import cn.zq.spshot.data.client.IssureClient;
import cn.zq.spshot.data.mapper.StatisticalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 华韵流风
 * @ClassName StatisticalService
 * @Date 2021/12/19 13:31
 * @packageName cn.zq.spshot.data.service
 * @Description TODO
 */
@Service
public class StatisticalService {

    @Resource
    private IssureClient issureClient;

    @Autowired
    private StatisticalDao statisticalDao;

    /**
     * 今日新增用户数
     *
     * @return Integer
     */
    public Long getTodayNewUser() {
        return statisticalDao.getTodayNewUser();
    }

    /**
     * 用户总数
     *
     * @return Integer
     */
    public Long getTotalUser() {
        return statisticalDao.getTotalUser();
    }

    /**
     * 本周新增用户数
     *
     * @return Integer
     */
    public Long getThisWeekNewUser() {
        return statisticalDao.getThisWeekNewUser();
    }

    /**
     * 本月新增会员数
     *
     * @return Long
     */
    public Long getThisMonthNewUser() {
        return statisticalDao.getThisMonthNewUser();
    }

    /**
     * 今日发布数
     *
     * @return Long
     */
    public Long getTodayIssureNumber() {
        return statisticalDao.getTodayIssureNumber();
    }

    /**
     * 今日参与数
     *
     * @return Long
     */
    public Long getTodayPartNumber() {
        return statisticalDao.getTodayPartNumber();
    }

    /**
     * 本周发布数
     *
     * @return Long
     */
    public Long getThisWeekIssureNumber() {
        return statisticalDao.getThisWeekIssureNumber();
    }

    /**
     * 本周参与数
     *
     * @return Long
     */
    public Long getThisWeekPartNumber() {
        return statisticalDao.getThisWeekPartNumber();
    }

    /**
     * 本月发布数
     *
     * @return long
     */
    public Long getThisMonthIssureNumber() {
        return statisticalDao.getThisMonthIssureNumber();
    }

    /**
     * 本月参与数
     *
     * @return long
     */
    public Long getThisMonthPartNumber() {
        return statisticalDao.getThisMonthPartNumber();
    }

    /**
     * 本日用户反馈数
     *
     * @return long
     */
    public Long getTodayFeedBackNumber() {
        return statisticalDao.getTodayFeedBackNumber();
    }

    /**
     * 今日处理反馈数
     *
     * @return Long
     */
    public Long getTodayDealNumber() {
        return statisticalDao.getTodayDealNumber();
    }

    /**
     * 本周反馈数
     *
     * @return Long
     */
    public Long getThisWeekFeedBackNumber() {
        return statisticalDao.getThisWeekFeedBackNumber();
    }

    /**
     * 本周处理数
     *
     * @return Long
     */
    public Long getThisWeekDealNumber() {
        return statisticalDao.getThisWeekDealNumber();
    }

    /**
     * 本月反馈数
     *
     * @return Long
     */
    public Long getThisMonthFeedBackNumber() {
        return statisticalDao.getThisMonthFeedBackNumber();
    }

    /**
     * 本月处理数
     *
     * @return long
     */
    public Long getThisMonthDealNumber() {
        return statisticalDao.getThisMonthDealNumber();
    }

    /**
     * 获取热门投票
     *
     * @return List<Map < String, Object>>
     */
    public List<Map<String, Object>> getHotIssure() {
        return (List<Map<String, Object>>) issureClient.getHotIssure().getData();
    }

}
