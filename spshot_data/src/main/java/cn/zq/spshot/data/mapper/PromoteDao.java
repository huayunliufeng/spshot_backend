package cn.zq.spshot.data.mapper;

import cn.zq.spshot.data.pojo.Promote;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/12/16
 *
 * @author 华韵流风
 */
@Repository
public interface PromoteDao {

    /**
     * 根据id查询promote
     *
     * @param id id
     * @return Promote
     */
    Promote findById(Integer id);

    /**
     * 模糊查询
     *
     * @param keyWord keyWord
     * @return List<Promote>
     */
    List<Promote> findByKeyWord(String keyWord);

    /**
     * 新增promote
     *
     * @param promote promote
     */
    void add(Promote promote);

    /**
     * 修改promote
     *
     * @param promote promote
     */
    void update(Promote promote);

    /**
     * 删除
     *
     * @param id id
     */
    void delete(Integer id);

    /**
     * 修改状态
     *
     * @param id    id
     * @param state state
     */
    void setState(Integer id, Integer state);

    /**
     * 查询上架推广总数
     *
     * @return Integer
     */
    Integer countRec();

    /**
     * 查询所有图片
     *
     * @return List<String>
     */
    List<String> getAllImages();

    /**
     * 查询上架推广
     *
     * @return List<Promote>
     */
    List<Promote> getRecPromote();
}
