package cn.zq.spshot.user.controller;

import cn.zq.spshot.common.aop.HasPermissions;
import cn.zq.spshot.common.entity.PageV;
import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.common.entity.StatusCode;
import cn.zq.spshot.common.utils.WeiXinUtil;
import cn.zq.spshot.user.pojo.User;
import cn.zq.spshot.user.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 华韵流风
 * @ClassName UserController
 * @Date 2021/11/27 21:58
 * @packageName cn.zq.spshot.user.controller
 * @Description TODO
 */
@RestController
@RequestMapping("/user")
@RefreshScope
//@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private WeiXinUtil weiXinUtil;


    /**
     * 增加用户
     *
     * @param user user
     * @return Result
     */
    @HasPermissions(values = {"USER_INSERT"})
    @PostMapping
    public Result add(@RequestBody User user) {
        try {
            userService.add(user);
            return new Result(StatusCode.OK, true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "添加失败");
    }

    /**
     * 模糊查询
     *
     * @param pageV pageV
     * @return Result
     */
    @HasPermissions(values = {"USER_SELECT"})
    @PostMapping("/search")
    public Result findUserPage(@RequestBody PageV pageV) {
        try {
            return new Result(StatusCode.OK, true, "", userService.findUserPage(pageV.getQueryString(), pageV.getCurrentPage(), pageV.getPageSize()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 根据ID查询User
     *
     * @param userId userId
     * @return Result
     */
    @GetMapping("/{userId}")
    public Result findById(@PathVariable Integer userId) {
        try {
            return new Result(StatusCode.OK, true, "查询成功", userService.findById(userId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 修改user
     *
     * @param userId userId
     * @param user   user
     * @return Result
     */
    @HasPermissions(values = {"USER_UPDATE"})
    @PutMapping("/{userId}")
    public Result update(@PathVariable int userId, @RequestBody User user) {
        try {
            user.setId(userId);
            userService.edit(user);
            return new Result(StatusCode.OK, true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "修改失败");
    }

    /**
     * 删除user
     *
     * @param userId userId
     * @return Result
     */
    @HasPermissions(values = {"USER_DELETE"})
    @DeleteMapping("/{userId}")
    public Result remove(@PathVariable int userId) {
        try {
            userService.remove(userId);
            return new Result(StatusCode.OK, true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "删除失败");
    }

    /**
     * 获取openid
     *
     * @param code code
     * @return Result
     */
    @GetMapping("/getOpenId/{code}")
    public Result getOpenId(@PathVariable String code){
        try {
            String openid = (String) weiXinUtil.getOpenId(code).get("openid");
            return new Result(StatusCode.OK,true,"",openid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL,false,"获取openid失败");
    }


    /**
     * 用户登录
     *
     * @param user user
     * @return Result
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        try {
            User login = userService.findByOpenId(user.getOpenid());
            //如果有说明存在
            if (login != null) {
                // 如果用户昵称和头像不为空说明需要更新数据
                if (!"".equals(user.getUsername()) && user.getUsername() != null) {
                    user.setId(login.getId());
                    userService.edit(user);
                }
                return new Result(StatusCode.OK, true, "登录成功", userService.findById(login.getId()));
            } else {
                //否则注册
                userService.add(user);
                return new Result(StatusCode.OK, true, "登录成功", user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "登录失败");
    }

    /**
     * 改变用户状态，1 禁参，2 禁发，0 正常，-1 禁止
     *
     * @param id id
     * @return Result
     */
    @HasPermissions(values = {"USER_UPDATE"})
    @GetMapping("/state/{id}/{state}")
    public Result changeState(@PathVariable int id, @PathVariable int state) {

        try {
            userService.changeState(id, state);
            return new Result(StatusCode.OK, true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "操作失败");
    }

}
