package cn.zq.spshot.user.mapper;

import cn.zq.spshot.user.pojo.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/12/13
 *
 * @author 华韵流风
 */
@Repository
public interface PermissionDao {

    /**
     * 查询所有permission
     *
     * @return List<Permission>
     */
    List<Permission> findAllPermission();

    /**
     * 根据id查询permission
     *
     * @param id id
     * @return Permission
     */
    Permission findPermissionById(Integer id);
}
