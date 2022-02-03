package cn.zq.spshot.user.service;

import cn.zq.spshot.common.entity.PageResult;
import cn.zq.spshot.common.entity.TemplateData;
import cn.zq.spshot.common.entity.WeiXinMessage;
import cn.zq.spshot.common.utils.WeiXinUtil;
import cn.zq.spshot.user.mapper.UserDao;
import cn.zq.spshot.user.pojo.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 华韵流风
 * @Date 2021-11-27 21:49:10
 */
@Service
public class UserService {

    @Autowired
    private WeiXinUtil weiXinUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserDao userDao;

    /**
     * 根据id查询User
     *
     * @param id id
     * @return User
     */
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    /**
     * 根据openid查询用户
     *
     * @param openId openId
     * @return User
     */
    public User findByOpenId(String openId) {
        return userDao.findByOpenId(openId);
    }

    /**
     * 根据id删除User
     *
     * @param id id
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer id) {
        userDao.delete(id);
    }

    /**
     * 新增User
     *
     * @param user user
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(User user) {
        user.setRegistime(Calendar.getInstance().getTime());
        userDao.add(user);
    }

    /**
     * 修改User
     *
     * @param user user
     */
    @Transactional(rollbackFor = Exception.class)
    public void edit(User user) {
        userDao.update(user);
    }

    /**
     * 模糊查询
     *
     * @param keyWord keyWord
     * @param page    page
     * @param size    size
     * @return PageResult
     */
    public PageResult<User> findUserPage(String keyWord, int page, int size) {
        PageHelper.startPage(page, size);
        Page<User> pageList = (Page<User>) userDao.findUserByKeyWord(keyWord);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 用户登录
     *
     * @param word word
     * @return User
     */
    public User login(String word) {
        return userDao.findUserByUsername(word);
    }

    /**
     * 改变用户状态，1 禁参，2 禁发，0 正常，-1 禁止
     *
     * @param id    id
     * @param state state
     */
    public void changeState(Integer id, Integer state) {
        userDao.changeState(id, state);
        //给相关用户发送通知
        String access_token = redisTemplate.opsForValue().get("access_token");
        String openid = userDao.getUserOpenId(id);
        Map<String, Object> map = new HashMap<>();
        map.put("date1", new TemplateData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
        if (state == 1) {
            map.put("thing3", new TemplateData("违规次数过多"));
            map.put("thing6", new TemplateData("禁止参与"));
        } else if (state == 2) {
            map.put("thing3", new TemplateData("违规次数过多"));
            map.put("thing6", new TemplateData("禁止发布"));
        } else if (state == 0) {
            map.put("thing3", new TemplateData("暂无"));
            map.put("thing6", new TemplateData("恢复正常"));
        } else {
            map.put("thing3", new TemplateData("违规次数过多"));
            map.put("thing6", new TemplateData("禁止参与和发布"));
        }
        WeiXinMessage weiXinMessage = new WeiXinMessage(access_token, openid, "", "", map);
        Map message = weiXinUtil.pushMessage(weiXinMessage);
        System.out.println(message);
        //释放redis连接
        RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
    }


}
