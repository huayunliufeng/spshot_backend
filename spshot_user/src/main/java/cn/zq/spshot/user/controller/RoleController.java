package cn.zq.spshot.user.controller;

import cn.zq.spshot.common.aop.HasPermissions;
import cn.zq.spshot.common.entity.PageV;
import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.common.entity.StatusCode;
import cn.zq.spshot.user.pojo.Role;
import cn.zq.spshot.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2021/12/9
 *
 * @author 华韵流风
 */
@RestController
@RefreshScope
//@CrossOrigin
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有角色
     *
     * @param page page
     * @param size size
     * @return Result
     */
    @HasPermissions(values = {"ROLE_SELECT"})
    @GetMapping("/{page}/{size}")
    public Result findAll(@PathVariable int page, @PathVariable int size) {
        try {

            return new Result(StatusCode.OK, true, "", roleService.findAll(page, size));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(StatusCode.FAIL, false, "查询失败");
    }


    /**
     * 条件查询并分页
     *
     * @param pageV pgeV
     * @return Result
     */
    @HasPermissions(values = {"ROLE_SELECT"})
    @PostMapping("/search")
    public Result findRolePage(@RequestBody PageV pageV) {
        try {
            return new Result(StatusCode.OK, true, "", roleService.findRolePage(pageV.getQueryString(), pageV.getCurrentPage(), pageV.getPageSize()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 根据ID查询Role
     *
     * @param roleId roleId
     * @return Result
     */
    @HasPermissions(values = {"ROLE_SELECT"})
    @GetMapping("/{roleId}")
    public Result findById(@PathVariable int roleId) {
        try {
            Map<String, Object> map = new HashMap<>();
            //角色详情
            map.put("role", roleService.findById(roleId));
            //包含的权限
            map.put("permissionIds", roleService.findRolePermissionIds(roleId));

            return new Result(StatusCode.OK, true, "", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 权限分页
     *
     * @param page page
     * @param size size
     * @return Result
     */
    @HasPermissions(values = {"ROLE_SELECT"})
    @GetMapping("/permission/{page}/{size}")
    public Result findAllPermissionPage(@PathVariable int page, @PathVariable int size) {
        try {
            //查询所有权限
            return new Result(StatusCode.OK, true, "", roleService.findAllPermission(page, size));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 禁用角色
     *
     * @param id id
     * @return Result
     */
    @HasPermissions(values = {"ROLE_UPDATE"})
    @GetMapping("/dis/{id}")
    public Result disRole(@PathVariable int id) {
        try {
            roleService.disRole(id);
            return new Result(StatusCode.OK, true, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "操作失败");
    }

    /**
     * 恢复角色
     *
     * @param id id
     * @return Result
     */
    @HasPermissions(values = {"ROLE_UPDATE"})
    @GetMapping("/rec/{id}")
    public Result recRole(@PathVariable int id) {
        try {
            roleService.recRole(id);
            return new Result(StatusCode.OK, true, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "操作失败");
    }

    /**
     * 新增角色
     *
     * @param permissionIds permissionIds
     * @param role          role
     * @return Result
     */
    @HasPermissions(values = {"ROLE_INSERT"})
    @PostMapping("/{permissionIds}")
    public Result addRole(@PathVariable int[] permissionIds, @RequestBody Role role) {
        try {
            roleService.add(role, permissionIds);
            return new Result(StatusCode.OK, true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "添加失败");
    }

    /**
     * 修改角色
     *
     * @param permissionIds permissionIds
     * @param role          role
     * @return Result
     */
    @HasPermissions(values = {"ROLE_UPDATE"})
    @PutMapping("/edit/{permissionIds}")
    public Result editRole(@PathVariable int[] permissionIds, @RequestBody Role role) {
        try {
            roleService.update(role, permissionIds);
            return new Result(StatusCode.OK, true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "修改失败");
    }

    /**
     * 删除角色
     *
     * @param roleId roleId
     * @return Result
     */
    @HasPermissions(values = {"ROLE_DELETE"})
    @DeleteMapping("/{roleId}")
    public Result delete(@PathVariable int roleId) {
        try {
            roleService.remove(roleId);
            return new Result(StatusCode.OK, true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "删除失败");
    }

}
