package cn.zq.spshot.issure.service;

import cn.zq.spshot.issure.mapper.ItemsDao;
import cn.zq.spshot.issure.pojo.Choose;
import cn.zq.spshot.issure.pojo.Item;
import cn.zq.spshot.issure.pojo.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created on 2021/12/13
 *
 * @author 华韵流风
 */
@Service
public class ItemsService {

    @Autowired
    private ItemsDao itemsDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 根据id查询问题
     *
     * @param id id
     * @return Items
     */
    public Items findById(Integer id) {
        Items items = itemsDao.findById(id);
        Query query = new Query();
        //查询答案
        query.addCriteria(Criteria.where("itemsid").is(id));
        //查询指定字段
        query.fields().include("ans");
        List<Item> item = mongoTemplate.find(query, Item.class);
        items.setItem(item);
        return items;
    }

    /**
     * 新增问题
     *
     * @param items items
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(List<Items> items) {
        for (Items item : items) {
            itemsDao.add(item);
            List<Item> item1 = item.getItem();
            for (Item item2 : item1) {
                //新增选项
                if (!"".equals(item2.getAns())) {
                    item2.setItemsid(item.getId());
                    mongoTemplate.insert(item2);
                }

            }
        }
    }

    /**
     * 修改问题
     *
     * @param items items
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(List<Items> items) {
        for (Items item : items) {
            if (findById(item.getId()) != null) {
                itemsDao.delete(item.getId());
            }
            itemsDao.add(item);
            List<Item> item1 = item.getItem();
            for (Item item2 : item1) {
                //修改问题
                Query query = new Query();
                query.addCriteria(Criteria.where("_id").is(item2.get_id()));
                //由于问题和选项数量不确定，因此先删除所有再添加
                if (mongoTemplate.findOne(query, Item.class) != null) {
                    mongoTemplate.remove(query, Item.class);
                }
                mongoTemplate.insert(item2);
            }
        }
    }

    /**
     * 设置选项
     *
     * @param chooses chooses
     */
    @Transactional(rollbackFor = Exception.class)
    public void part(List<Choose> chooses) {
        for (Choose choose : chooses) {
            mongoTemplate.insert(choose);
        }
    }

    /**
     * 统计选项人数
     *
     * @param itemsid itemsid
     * @param choose  choose
     * @return long
     */
    public long countChange(Integer itemsid, String choose) {
        Query query = new Query();
        query.addCriteria(Criteria.where("itemsid").is(itemsid).and("choose").is(choose));
        return mongoTemplate.count(query, Choose.class);
    }

    /**
     * 查询用户选项
     *
     * @param userid  userid
     * @param itemsid itemsid
     * @return String
     */
    public List<String> findUserChoose(Integer userid, Integer itemsid) {
        List<String> res = new ArrayList<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("userid").is(userid).and("itemsid").is(itemsid));
        List<Choose> list = mongoTemplate.find(query, Choose.class);
        for (Choose choose : list) {
            res.add(choose.getChoose());
        }
        return res;
    }
}
