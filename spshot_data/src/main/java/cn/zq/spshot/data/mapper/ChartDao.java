package cn.zq.spshot.data.mapper;

import org.springframework.stereotype.Repository;

/**
 * @author 华韵流风
 * @ClassName ChartDao
 * @Date 2021/12/19 16:35
 * @packageName cn.zq.spshot.data.mapper
 * @Description TODO
 */
@Repository
public interface ChartDao {

    /**
     * 查询用户数据
     *
     * @param currYear currYear
     * @param month    month
     * @return int
     */
    int getUserData(Integer currYear, int month);

    /**
     * 查询用户反馈数据
     *
     * @param currYear currYear
     * @param month    month
     * @return int
     */
    int getFeedbackData(Integer currYear, int month);

    /**
     * 总投票数据
     *
     * @param currYear currYear
     * @param month    month
     * @return int
     */
    int getIssureData(Integer currYear, int month);

    /**
     * 查询通过的投票数据
     *
     * @param currYear currYear
     * @param month    month
     * @return int
     */
    int getPassData(Integer currYear, int month);

    /**
     * 参与投票数据
     *
     * @param currYear currYear
     * @param month    month
     * @return int
     */
    int getPartData(Integer currYear, int month);
}
