package cn.zq.spshot.data.service;

import cn.zq.spshot.common.entity.PageResult;
import cn.zq.spshot.data.mapper.FeedBackDao;
import cn.zq.spshot.data.pojo.FeedBack;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Map;

/**
 * Created on 2021/12/16
 *
 * @author 华韵流风
 */
@Service
public class FeedBackService {

    @Autowired
    private FeedBackDao feedBackDao;

    /**
     * 根据id查feedback
     *
     * @param id id
     * @return FeedBack
     */
    public FeedBack findById(Integer id) {
        return feedBackDao.findById(id);
    }

    /**
     * 查询用户未读消息
     *
     * @param userid userid
     * @return Integer
     */
    public Integer getDealIsNotRead(Integer userid) {
        return feedBackDao.getDealIsNotRead(userid);
    }

    /**
     * 模糊查询
     *
     * @param keyWord keyWord
     * @param state   state
     * @param page    page
     * @param size    size
     * @return PageResult
     */
    public PageResult<FeedBack> findAllFeedBack(String keyWord, Integer state, int page, int size) {
        PageHelper.startPage(page, size);
        Page<FeedBack> pageList = (Page<FeedBack>) feedBackDao.findAllFeedBack(keyWord, state);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 查询处理结果
     *
     * @param keyWord keyWord
     * @param page    page
     * @param size    size
     * @return PageResult
     */
    public PageResult<Map<String, Object>> findDealPage(String keyWord, int page, int size) {
        PageHelper.startPage(page, size);
        Page<Map<String, Object>> pageList = (Page<Map<String, Object>>) feedBackDao.findDealPage(keyWord);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 查询指定用户的系统消息
     *
     * @param userid userid
     * @param page   page
     * @param size   size
     * @return PageResult
     */
    public PageResult<Map<String, Object>> findUserMessage(Integer userid, int page, int size) {
        PageHelper.startPage(page, size);
        Page<Map<String, Object>> pageList = (Page<Map<String, Object>>) feedBackDao.findUserMessage(userid);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 根据id查询deal
     *
     * @param id id
     * @return Map<String, Object>
     */
    public Map<String, Object> findDealById(Integer id) {
        return feedBackDao.findDealById(id);
    }

    /**
     * 添加feedback
     *
     * @param feedBack feedBack
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(FeedBack feedBack) {
        feedBack.setCreatetime(Calendar.getInstance().getTime());
        feedBackDao.add(feedBack);
    }

    /**
     * 修改feedback
     *
     * @param feedBack feedBack
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(FeedBack feedBack) {
        feedBackDao.update(feedBack);
    }

    /**
     * 删除feedback
     *
     * @param id id
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        feedBackDao.delete(id);
    }

    /**
     * 处理反馈
     *
     * @param map map
     */
    @Transactional(rollbackFor = Exception.class)
    public void deal(Map<String, Object> map) {
        map.put("dealtime", Calendar.getInstance().getTime());
        feedBackDao.deal(map);
        feedBackDao.setState((Integer) map.get("fbid"));
    }

    /**
     * 返回未处理反馈数
     *
     * @return long
     */
    public long countNoDeal() {
        return feedBackDao.countNoDeal();
    }

    /**
     * 设置已读
     *
     * @param fbid fbid
     */
    @Transactional(rollbackFor = Exception.class)
    public void setIsRead(Integer fbid) {
        feedBackDao.setIsRead(fbid);
    }

}
