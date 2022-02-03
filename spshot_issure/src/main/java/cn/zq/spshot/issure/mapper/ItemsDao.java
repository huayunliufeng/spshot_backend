package cn.zq.spshot.issure.mapper;

import cn.zq.spshot.issure.pojo.Items;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created on 2021/12/14
 *
 * @author 华韵流风
 */
@Repository
public interface ItemsDao {

    /**
     * 根据id查询items
     *
     * @param id id
     * @return Items
     */
    Items findById(Integer id);

    /**
     * 查询所有items
     *
     * @return List<Items>
     */
    List<Items> findAllItems();

    /**
     * 新增items
     *
     * @param items items
     */
    void add(Items items);

    /**
     * 修改items
     *
     * @param items items
     */
    void update(Items items);

    /**
     * 删除items
     *
     * @param id id
     */
    void delete(Integer id);
}
