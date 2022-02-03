package cn.zq.spshot.data.mapper;

import cn.zq.spshot.data.pojo.FeedBack;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created on 2021/12/16
 *
 * @author 华韵流风
 */
@Repository
public interface FeedBackDao {

    /**
     * 根据id查询feedback
     *
     * @param id id
     * @return FeedBack
     */
    FeedBack findById(Integer id);

    /**
     * 查询用户未读消息数
     *
     * @param userid userid
     * @return getDealIsNotRead
     */
    Integer getDealIsNotRead(Integer userid);

    /**
     * 按条件查询feedback,0 未处理，1 已处理
     *
     * @param keyWord keyWord
     * @param state   state
     * @return List<FeedBack>
     */
    List<FeedBack> findAllFeedBack(String keyWord, Integer state);

    /**
     * 查询处理结果
     *
     * @param keyWord keyWord
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> findDealPage(String keyWord);

    /**
     * 查询指定用户的系统消息
     *
     * @param userid userid
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> findUserMessage(Integer userid);

    /**
     * 根据id查询deal
     *
     * @param id id
     * @return Map<String, Object>
     */
    Map<String, Object> findDealById(Integer id);

    /**
     * 新增feedback
     *
     * @param feedBack feedBack
     */
    void add(FeedBack feedBack);

    /**
     * 修改feedback
     *
     * @param feedBack feedBack
     */
    void update(FeedBack feedBack);

    /**
     * 删除feedback
     *
     * @param id id
     */
    void delete(Integer id);

    /**
     * 处理反馈
     *
     * @param map map
     */
    void deal(Map<String, Object> map);

    /**
     * 设置状态
     *
     * @param id id
     */
    void setState(Integer id);

    /**
     * 查询未处理反馈数量
     *
     * @return Long
     */
    long countNoDeal();

    /**
     * 设置已读
     *
     * @param fbid fbid
     */
    void setIsRead(Integer fbid);
}
