package cn.zq.spshot.user.mapper;


import cn.zq.spshot.user.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/12/13
 *
 * @author 华韵流风
 */
@Repository
public interface RoleDao {

    /**
     * 根据id查询role
     *
     * @param id id
     * @return Role
     */
    Role findById(Integer id);

    /**
     * 查询所有Role
     *
     * @return List<Role>
     */
    List<Role> findAllRole();

    /**
     * 新增role
     *
     * @param role role
     */
    void add(Role role);

    /**
     * 修改role
     *
     * @param role role
     */
    void update(Role role);

    /**
     * 删除role
     *
     * @param id id
     */
    void delete(Integer id);

    /**
     * 禁用role
     *
     * @param id id
     */
    void disRole(Integer id);

    /**
     * 恢复role
     *
     * @param id id
     */
    void recRole(Integer id);

    /**
     * 为role添加permission
     *
     * @param id            id
     * @param permissionIds permissionIds
     */
    void insertRolePermiss(Integer id, int[] permissionIds);

    /**
     * 删除role的权限
     *
     * @param id id
     */
    void deleteRolePermiss(Integer id);

    /**
     * 模糊查询
     *
     * @param keyWord keyWord
     * @return List<Role>
     */
    List<Role> findRoleByKeyWord(String keyWord);

    /**
     * 查询role所属权限id
     *
     * @param id id
     * @return List<Integer>
     */
    List<Integer> findRolePermissionIds(Integer id);


}
