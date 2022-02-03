package cn.zq.spshot.data.service;

import cn.zq.spshot.common.entity.PageResult;
import cn.zq.spshot.data.mapper.PromoteDao;
import cn.zq.spshot.data.pojo.Promote;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created on 2021/12/16
 *
 * @author 华韵流风
 */
@Service
public class PromoteService {

    @Autowired
    private PromoteDao promoteDao;

    /**
     * 根据id查询promote
     *
     * @param id id
     * @return Promote
     */
    public Promote findById(Integer id) {
        return promoteDao.findById(id);
    }

    /**
     * 模糊查询
     *
     * @param keyWord keyWord
     * @param page    page
     * @param size    size
     * @return PageResult<Promote>
     */
    public PageResult<Promote> findByKeyWord(String keyWord, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        Page<Promote> pageList = (Page<Promote>) promoteDao.findByKeyWord(keyWord);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 新增promote
     *
     * @param promote promote
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(Promote promote) {
        promote.setCreatetime(Calendar.getInstance().getTime());
        promoteDao.add(promote);
    }

    /**
     * 修改promote
     *
     * @param promote promote
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Promote promote) {
        promoteDao.update(promote);
    }

    /**
     * 删除promote
     *
     * @param id id
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        promoteDao.delete(id);
    }

    /**
     * 上架和下架
     *
     * @param id    id
     * @param state state
     */
    @Transactional(rollbackFor = Exception.class)
    public void setState(Integer id, Integer state) {
        promoteDao.setState(id, state);
    }

    /**
     * 查询上架推广数
     *
     * @return Integer
     */
    public Integer countRec() {
        return promoteDao.countRec();
    }

    /**
     * 查询所有图片
     *
     * @return List<String>
     */
    public List<String> getAllImages() {
        return promoteDao.getAllImages();
    }

    /**
     * 获取上架的推广
     *
     * @return List<Promote>
     */
    public List<Promote> getRecPromote() {
        return promoteDao.getRecPromote();
    }

}
