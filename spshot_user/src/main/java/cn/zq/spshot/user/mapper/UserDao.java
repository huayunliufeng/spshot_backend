package cn.zq.spshot.user.mapper;

import cn.zq.spshot.user.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/12/13
 *
 * @author 华韵流风
 */
@Repository
public interface UserDao {

    /**
     * 根据id查询user
     *
     * @param id id
     * @return User
     */
    User findById(Integer id);

    /**
     * 根据openId查询用户（草，一种植物。不提供获取用户微信号接口，只能如此确定用户唯一性）
     *
     * @param openId openId
     * @return User
     */
    User findByOpenId(String openId);

    /**
     * 新增user
     *
     * @param user user
     */
    void add(User user);

    /**
     * 更新user
     *
     * @param user user
     */
    void update(User user);

    /**
     * 删除user
     *
     * @param id id
     */
    void delete(Integer id);

    /**
     * 模糊查询
     *
     * @param keyWord keyWord
     */
    List<User> findUserByKeyWord(String keyWord);

    /**
     * 根据用户名查询user
     *
     * @param word word
     * @return user
     */
    User findUserByUsername(String word);

    /**
     * 改变用户状态，1 禁参，2 禁发，0 正常，-1 禁止
     *
     * @param id    id
     * @param state state
     */
    void changeState(Integer id, Integer state);

    /**
     * 获取用户openid
     *
     * @param id id
     * @return String
     */
    String getUserOpenId(Integer id);
}
