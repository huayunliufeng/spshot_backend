package cn.zq.spshot.data.service;

import cn.zq.spshot.data.mapper.ChartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 华韵流风
 * @ClassName ChartService
 * @Date 2021/12/19 16:36
 * @packageName cn.zq.spshot.data.service
 * @Description TODO
 */
@Service
public class ChartService {

    @Autowired
    private ChartDao chartDao;

    /**
     * 查询用户数据
     *
     * @param currYear currYear
     * @param xArray   xArray
     * @return int[]
     */
    public int[] getUserData(Integer currYear, int[] xArray) {

        int[] data = new int[xArray.length];
        for (int i = 0; i < xArray.length; i++) {
            data[i] = chartDao.getUserData(currYear, xArray[i]);
        }
        return data;
    }

    /**
     * 查询用户反馈数据
     *
     * @param currYear currYear
     * @param xArray   xArray
     * @return int[]
     */
    public int[] getFeedbackData(Integer currYear, int[] xArray) {
        int[] data = new int[xArray.length];
        for (int i = 0; i < xArray.length; i++) {
            data[i] = chartDao.getFeedbackData(currYear, xArray[i]);
        }
        return data;
    }

    /**
     * 投票数据
     *
     * @param currYear currYear
     * @param xArray   xArray
     * @return int[]
     */
    public int[][] getIssureData(Integer currYear, int[] xArray) {
        int[][] data = new int[2][xArray.length];
        for (int i = 0; i < xArray.length; i++) {
            data[0][i] = chartDao.getIssureData(currYear, xArray[i]);
            data[1][i] = chartDao.getPassData(currYear, xArray[i]);
        }
        return data;
    }

    /**
     * 参与投票数据
     *
     * @param currYear currYear
     * @param xArray   xArray
     * @return int[]
     */
    public int[] getPartData(Integer currYear, int[] xArray) {
        int[] data = new int[xArray.length];
        for (int i = 0; i < xArray.length; i++) {
            data[i] = chartDao.getPartData(currYear, xArray[i]);
        }
        return data;
    }


}
