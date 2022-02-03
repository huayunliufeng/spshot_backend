package cn.zq.spshot.data.mapper;

import org.springframework.stereotype.Repository;

/**
 * @author 华韵流风
 * @ClassName StatisticalDao
 * @Date 2021/12/19 13:34
 * @packageName cn.zq.spshot.data.mapper
 * @Description TODO
 */
@Repository
public interface StatisticalDao {

    /**
     * 今日新增会员数
     *
     * @return Integer
     */
    Long getTodayNewUser();

    /**
     * 用户总数
     *
     * @return Integer
     */
    Long getTotalUser();

    /**
     * 本周新增用户数
     *
     * @return Integer
     */
    Long getThisWeekNewUser();

    /**
     * 本月新增会员数
     *
     * @return Long
     */
    Long getThisMonthNewUser();

    /**
     * 今日发布数
     *
     * @return Long
     */
    Long getTodayIssureNumber();

    /**
     * 今日参与数
     *
     * @return Long
     */
    Long getTodayPartNumber();

    /**
     * 本周发布数
     *
     * @return Long
     */
    Long getThisWeekIssureNumber();

    /**
     * 本周参与数
     *
     * @return Long
     */
    Long getThisWeekPartNumber();

    /**
     * 本月发布数
     *
     * @return long
     */
    Long getThisMonthIssureNumber();

    /**
     * 本月参与数
     *
     * @return long
     */
    Long getThisMonthPartNumber();

    /**
     * 本日用户反馈数
     *
     * @return long
     */
    Long getTodayFeedBackNumber();

    /**
     * 今日处理反馈数
     *
     * @return Long
     */
    Long getTodayDealNumber();

    /**
     * 本周反馈数
     *
     * @return Long
     */
    Long getThisWeekFeedBackNumber();

    /**
     * 本周处理数
     *
     * @return Long
     */
    Long getThisWeekDealNumber();

    /**
     * 本月反馈数
     *
     * @return Long
     */
    Long getThisMonthFeedBackNumber();

    /**
     * 本月处理数
     *
     * @return long
     */
    Long getThisMonthDealNumber();

}
