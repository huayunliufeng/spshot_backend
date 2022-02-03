package cn.zq.spshot.user.service;

import cn.zq.spshot.common.entity.PageResult;
import cn.zq.spshot.common.utils.RSAUtil;
import cn.zq.spshot.user.mapper.AdminDao;
import cn.zq.spshot.user.pojo.Admin;
import cn.zq.spshot.user.pojo.Menu;
import cn.zq.spshot.user.pojo.Permission;
import cn.zq.spshot.user.pojo.Role;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 华韵流风
 * @Date 2021-11-27 21:49:10
 */
@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RoleService roleService;

    /**
     * 根据id查询Admin
     *
     * @param id id
     * @return Admin
     */
    public Admin findById(Integer id) {
        return adminDao.findById(id);
    }

    /**
     * 根据id删除Admin
     *
     * @param id id
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer id) {
        adminDao.delete(id);
    }

    /**
     * 新增Admin
     *
     * @param admin admin
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(Admin admin, int[] roleIds) {
        admin.setPassword(encoder.encode(admin.getPassword()));
        admin.setIsdel("0");
        admin.setState(0);
        adminDao.add(admin);
        if (roleIds.length != 0) {
            adminDao.insertAdminRole(admin.getId(), roleIds);
        }
    }

    /**
     * 修改admin
     *
     * @param admin   admin
     * @param roleIds roleIds
     */
    @Transactional(rollbackFor = Exception.class)
    public void editAdminRoles(Admin admin, int[] roleIds) {
        adminDao.deleteAdminRoles(admin.getId());
        if (roleIds.length != 0) {
            adminDao.insertAdminRole(admin.getId(), roleIds);
        }

        if (admin.getPassword().length() <= 18) {
            admin.setPassword(encoder.encode(admin.getPassword()));
        }
        adminDao.update(admin);
    }


    /**
     * 禁用管理员
     *
     * @param adminid adminid
     */
    @Transactional(rollbackFor = Exception.class)
    public void disAdmin(int adminid) {
        adminDao.disAdmin(adminid);
    }

    /**
     * 恢复管理员
     *
     * @param adminid adminid
     */
    @Transactional(rollbackFor = Exception.class)
    public void recAdmin(int adminid) {
        adminDao.recAdmin(adminid);
    }

    /**
     * 查询所有Admin
     *
     * @return List<Admin>
     */
    public List<Admin> findAll() {
        return adminDao.findAllAdmin();
    }

    /**
     * 查询菜单
     *
     * @param adminid adminid
     * @return Map<String, Object>
     */
    public Map<String, Object> getMenu(Integer adminid) {
        Map<String, Object> map = new HashMap<>();
        Set<Integer> menuIds = getMenuIds(adminid);
        List<String> menuPath = getMenuPath(menuIds);
        Set<Menu> menus = new HashSet<>();
        //查询父菜单
        Set<Menu> parentMenu = adminDao.findMenu(menuIds, 0);
        //查询子菜单
        for (Menu menu : parentMenu) {
            Set<Menu> childMenu = adminDao.findMenu(menuIds, menu.getId());
            menu.setChildren(childMenu);
            menus.add(menu);
        }
        map.put("menuPath", menuPath);
        map.put("menus", menus);
        return map;
    }

    /**
     * 查询菜单路径
     *
     * @param menuIds menuIds
     * @return List<String>
     */
    private List<String> getMenuPath(Set<Integer> menuIds) {
        return adminDao.getMenuPath(menuIds);
    }

    /**
     * 获取菜单id
     *
     * @param adminid adminid
     * @return Set<Integer>
     */
    private Set<Integer> getMenuIds(Integer adminid) {
        List<Integer> roleIds = adminDao.findAdminRoles(adminid);
        Set<Integer> menuIds = new HashSet<>();
        for (Integer roleId : roleIds) {
            List<Integer> menuRole = adminDao.findMenuRole(roleId);
            menuIds.addAll(menuRole);
        }
        return menuIds;
    }


    /**
     * 登录
     *
     * @param loginname loginname
     * @param password  password
     * @return Admin
     */
    public Admin login(String loginname, String password) {
        Admin admin = adminDao.findAdminByLoginname(loginname);
        if (admin != null && encoder.matches(password, admin.getPassword()) && admin.getState() == 0) {
            return admin;
        } else {
            return null;
        }
    }

    /**
     * 管理员分页
     *
     * @param keyWord keyWord
     * @param page    page
     * @param size    size
     * @return Page<Admin>
     */
    public PageResult<Admin> findByAdminPage(String keyWord, int page, int size) {
        PageHelper.startPage(page, size);
        Page<Admin> pageList = (Page<Admin>) adminDao.findByKeyWord(keyWord);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 查询管理员所属角色
     *
     * @param id id
     * @return int[]
     */
    public List<Integer> findAdminRole(Integer id) {
        return adminDao.findAdminRoles(id);
    }

    /**
     * 查询管理员权限
     *
     * @param id id
     * @return Set<String>
     */
    public Set<String> findAdminPerMissions(Integer id) {
        Set<String> permissions = new HashSet<>();
            List<Integer> adminRoles = adminDao.findAdminRoles(id);
            for (Integer adminRole : adminRoles) {
                Role role = roleService.findById(adminRole);
                List<Integer> rolePermissionIds = roleService.findRolePermissionIds(role.getId());
                for (Integer rolePermissionId : rolePermissionIds) {
                    Permission permission = roleService.findPermissionById(rolePermissionId);
                    permissions.add(permission.getKeyword());
                }
                permissions.add(role.getKeyword());
            }
        return permissions;
    }


}
