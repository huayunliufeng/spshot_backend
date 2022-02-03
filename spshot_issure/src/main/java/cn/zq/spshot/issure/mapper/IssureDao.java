package cn.zq.spshot.issure.mapper;

import cn.zq.spshot.issure.pojo.Issure;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created on 2021/12/14
 *
 * @author 华韵流风
 */
@Repository
public interface IssureDao {

    /**
     * 根据id查询issure
     *
     * @param id id
     * @return Issure
     */
    Issure findById(Integer id);

    /**
     * 模糊查询
     *
     * @param state   state
     * @param keyWord keyword
     * @return List<Issure>
     */
    List<Issure> findIssureByKeyWord(String keyWord, Integer state);

    /**
     * 查询热门投票
     *
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getHotIssure();

    /**
     * 模糊查询
     *
     * @param state   state
     * @param keyWord keyword
     * @param adminid adminid
     * @return List<Issure>
     */
    List<Issure> findIssureByKeyWordOP(String keyWord, Integer state, Integer adminid);

    /**
     * 查询通过审核的投票
     *
     * @param state    state
     * @param keyWord  keyword
     * @param isexpire isexpire
     * @return List<Issure>
     */
    List<Issure> findIssureByKeyWordOngoing(String keyWord, Integer state, Integer isexpire);

    /**
     * 新增issure
     *
     * @param issure issure
     */
    void add(Issure issure);

    /**
     * 新增issure记录
     *
     * @param issureid issureid
     * @param itemid   itemid
     */
    void addIssureItem(Integer issureid, Integer itemid);

    /**
     * 新增verify
     *
     * @param map map
     */
    void addVerify(Map<String, Object> map);

    /**
     * 修改issure
     *
     * @param issure issure
     */
    void update(Issure issure);

    /**
     * 删除issure
     *
     * @param id id
     */
    void delete(Integer id);

    /**
     * 查询审核记录
     *
     * @param keyWord keyWord
     * @return List<Map < String, String>>
     */
    List<Map<String, Object>> findVerifyByKeyword(String keyWord);


    /**
     * 查询未审核数
     *
     * @param adminid adminid
     * @return Integer
     */
    Integer findUnVerifyCount(Integer adminid);

    /**
     * 根据id查询issure的items
     *
     * @param id id
     * @return List<Integer>
     */
    List<Integer> findIssureItems(Integer id);

    /**
     * 改变审核状态：0 未审核，1 通过，-1 未通过，2 封禁
     *
     * @param id    id
     * @param state state
     */
    void changState(Integer id, Integer state);

    /**
     * 设置参与码
     *
     * @param id       id
     * @param partcode partcode
     */
    void setPartCode(Integer id, String partcode);

    /**
     * 设置审核时间
     *
     * @param id         id
     * @param accepttime accepttime
     */
    void setAccepttime(Integer id, Date accepttime);

    /**
     * 设置今日发布数
     *
     * @param id id
     */
    void setTodayNum(Integer id);

    /**
     * 重置今日发布数
     */
    void recTodayNum();

    /**
     * 设置总发布数
     *
     * @param id id
     */
    void setTotalIssure(Integer id);

    /**
     * 设置总参与数
     *
     * @param id id
     */
    void setTotalPart(Integer id);

    /**
     * 设置过期
     *
     * @param id id
     */
    void setIssureExpire(Integer id);

    /**
     * 获取参与者id
     *
     * @param issureid issureid
     * @return List<Integer>
     */
    List<Integer> getUserIdFromPart(Integer issureid);

    /**
     * 查询已公开且通过审核的投票
     *
     * @param keyWord keyWord
     * @return List<Issure>
     */
    List<Issure> findPublicAndAccessIssure(String keyWord);

    /**
     * 查询公开和未公开的
     *
     * @param keyWord keyWord
     * @return List<Issure>
     */
    List<Issure> findIsNotPublic(String keyWord);

    /**
     * 获取发布者id
     *
     * @param id id
     * @return Integer
     */
    Integer getUserIdFromIssure(Integer id);

    /**
     * 获取openid
     *
     * @param id id
     * @return String
     */
    String getOpenId(Integer id);

    /**
     * 新增参与记录
     *
     * @param map map
     */
    void addPart(Map<String, Object> map);

    /**
     * 设置当前参与人数+1
     *
     * @param id id
     */
    void setCurrPerson(Integer id);

    /**
     * 查询用户发布的投票
     *
     * @param userid  userid
     * @param type    type
     * @param keyWord keyWord
     */
    List<Issure> getIssureType(Integer userid, Integer type, String keyWord);

    /**
     * 查询用户参与的投票id
     *
     * @param userid userid
     * @return List<Integer>
     */
    List<Integer> getUserPart(Integer userid);

    /**
     * 查询用户参与的投票
     *
     * @param ids     ids
     * @param type    type
     * @param keyWord keyWord
     * @return List<Issure>
     */
    List<Issure> findUserPartIssureByType(List<Integer> ids, Integer type, String keyWord);

    /**
     * 根据issureid查询openid
     *
     * @param issureid issureid
     * @return String
     */
    String getOpenIdByIssureId(Integer issureid);

    /**
     * 获取指定投票当当前参与人数
     *
     * @param issureid issureid
     * @return Integer
     */
    Integer getIssureCurrPerson(Integer issureid);

}
