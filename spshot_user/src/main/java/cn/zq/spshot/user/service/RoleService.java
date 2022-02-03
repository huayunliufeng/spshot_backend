package cn.zq.spshot.user.service;

import cn.zq.spshot.common.entity.PageResult;
import cn.zq.spshot.user.mapper.PermissionDao;
import cn.zq.spshot.user.mapper.RoleDao;
import cn.zq.spshot.user.pojo.Permission;
import cn.zq.spshot.user.pojo.Role;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 2021/12/9
 *
 * @author 华韵流风
 */
@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 根据id查询role
     *
     * @param id id
     * @return Role
     */
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    /**
     * 根据id删除role
     *
     * @param id id
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer id) {
        roleDao.delete(id);
    }

    /**
     * 添加role
     *
     * @param role role
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(Role role, int[] permissionIds) {
        role.setIsdel("0");
        role.setState(0);
        roleDao.add(role);

        if (permissionIds.length != 0) {
            roleDao.insertRolePermiss(role.getId(), permissionIds);
        }
    }

    /**
     * 修改role
     *
     * @param permissionIds permissionIds
     * @param role          role
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Role role, int[] permissionIds) {
        roleDao.deleteRolePermiss(role.getId());
        if (permissionIds.length != 0) {
            roleDao.insertRolePermiss(role.getId(), permissionIds);
        }
        roleDao.update(role);
    }

    /**
     * 禁用角色
     *
     * @param id id
     */
    @Transactional(rollbackFor = Exception.class)
    public void disRole(int id) {
        roleDao.disRole(id);
    }

    /**
     * 恢复角色
     *
     * @param id id
     */
    @Transactional(rollbackFor = Exception.class)
    public void recRole(int id) {
        roleDao.recRole(id);
    }

    /**
     * 查询所有role
     *
     * @return List<Role>
     */
    public PageResult<Role> findAll(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Role> pageList = (Page<Role>) roleDao.findAllRole();
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }


    /**
     * 条件查询并分页
     *
     * @param page page
     * @param size size
     * @return Page<Role>
     */
    public PageResult<Role> findRolePage(String keyWord, int page, int size) {
        PageHelper.startPage(page, size);
        Page<Role> pageList = (Page<Role>) roleDao.findRoleByKeyWord(keyWord);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 查询角色所属权限id
     *
     * @param roleId roleId
     * @return List<Integer>
     */
    public List<Integer> findRolePermissionIds(Integer roleId) {
        return roleDao.findRolePermissionIds(roleId);
    }


    /**
     * 查询所有权限
     *
     * @param page page
     * @param size size
     * @return Page<Permission>
     */
    public PageResult<Permission> findAllPermission(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Permission> pageList = (Page<Permission>) permissionDao.findAllPermission();
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 根据id查询permission
     *
     * @param id id
     * @return Permission
     */
    public Permission findPermissionById(Integer id) {
        return permissionDao.findPermissionById(id);
    }


}
