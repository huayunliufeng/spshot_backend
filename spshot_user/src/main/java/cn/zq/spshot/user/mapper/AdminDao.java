package cn.zq.spshot.user.mapper;

import cn.zq.spshot.user.pojo.Admin;
import cn.zq.spshot.user.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created on 2021/12/13
 *
 * @author 华韵流风
 */
@Repository
public interface AdminDao {

    /**
     * 根据id查询admin
     *
     * @param id id
     * @return Admin
     */
    Admin findById(Integer id);

    /**
     * 查询所有admin
     *
     * @return List<Admin>
     */
    List<Admin> findAllAdmin();

    /**
     * 新增admin
     *
     * @param admin admin
     */
    void add(Admin admin);

    /**
     * 修改admin
     *
     * @param admin admin
     */
    void update(Admin admin);

    /**
     * 删除admin
     *
     * @param id id
     */
    void delete(Integer id);

    /**
     * 禁用admin
     *
     * @param id id
     */
    void disAdmin(Integer id);

    /**
     * 恢复admin
     *
     * @param id id
     */
    void recAdmin(Integer id);

    /**
     * 为管理员添加角色
     *
     * @param id      id
     * @param roleIds roleIds
     */
    void insertAdminRole(Integer id, int[] roleIds);

    /**
     * 清空管理员角色
     *
     * @param id id
     */
    void deleteAdminRoles(Integer id);

    /**
     * 根据账号查询管理员
     *
     * @param loginname loginname
     * @return Admin
     */
    Admin findAdminByLoginname(String loginname);

    /**
     * 模糊查询
     *
     * @param keyWord keyWord
     * @return List<Admin>
     */
    List<Admin> findByKeyWord(String keyWord);

    /**
     * 查询管理员所属角色
     *
     * @param id id
     * @return List<Integer>
     */
    List<Integer> findAdminRoles(Integer id);

    /**
     * 查询角色的菜单id
     *
     * @param roleid roleid
     * @return List<Integer>
     */
    List<Integer> findMenuRole(Integer roleid);

    /**
     * 查询菜单
     *
     * @param menuIds  menuIds
     * @param parentid parentid
     * @return Set<Menu>
     */
    Set<Menu> findMenu(Set<Integer> menuIds, Integer parentid);

    /**
     * 查询菜单路径
     *
     * @param menuIds menuIds
     * @return List<String>
     */
    List<String> getMenuPath(Set<Integer> menuIds);

}
