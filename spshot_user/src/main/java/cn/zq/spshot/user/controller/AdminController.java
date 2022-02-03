package cn.zq.spshot.user.controller;

import cn.zq.spshot.common.aop.HasPermissions;
import cn.zq.spshot.common.entity.PageV;
import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.common.entity.StatusCode;
import cn.zq.spshot.common.utils.Base64;
import cn.zq.spshot.common.utils.JwtUtil;
import cn.zq.spshot.common.utils.RSAUtil;
import cn.zq.spshot.common.utils.VerifyCodeUtils;
import cn.zq.spshot.user.pojo.Admin;
import cn.zq.spshot.user.service.AdminService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 华韵流风
 * @ClassName AdminController
 * @Date 2021/11/27 21:58
 * @packageName cn.zq.spshot.user.controller
 * @Description TODO
 */
@RestController
@RequestMapping("/admin")
//@CrossOrigin
@RefreshScope
public class AdminController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private HttpServletRequest request;


    /**
     * 获取公钥
     *
     * @return Result
     */
    @GetMapping(value = "/getPublicKey")
    public Result getPublicKey() {
        try {
            return new Result(StatusCode.OK, true, "", RSAUtil.generateBase64PublicKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "获取公钥失败！请联系管理员或者刷新重试");
    }

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage/{uuid}")
    public Result getCode(@PathVariable String uuid) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            // 生成随机字串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            if ("1".equals(uuid)) {
                uuid = UUID.randomUUID().toString().replaceAll("-", "");
            }
            // 生成图片
            int w = 120, h = 40;
            VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
            Map<String, String> map = new HashMap<>();
            map.put("img", "data:image/gif;base64," + Base64.encode(stream.toByteArray()));
            map.put("uuid", uuid);
            redisTemplate.opsForValue().set("verifyCode_" + uuid, verifyCode, 5, TimeUnit.MINUTES);
            return new Result(StatusCode.OK, true, "", map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        }
        return new Result(StatusCode.VERIFICATION_CODE_ERROR, false, "生成验证码失败");
    }

    /**
     * 增加管理员
     *
     * @param admin admin
     * @return Result
     */
    @HasPermissions(values = {"ADMIN_INSERT"})
    @PostMapping("/{roleIds}")
    public Result add(@PathVariable int[] roleIds, @RequestBody Admin admin) {
        try {
            admin.setLoginname(RSAUtil.decryptBase64(admin.getLoginname()));
            admin.setPassword(RSAUtil.decryptBase64(admin.getPassword()));
            adminService.add(admin, roleIds);
            return new Result(StatusCode.OK, true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "添加失败，可能是名称重复");
    }


    /**
     * 管理员全部列表
     *
     * @return Result
     */
    @HasPermissions(values = {"ADMIN_SELECT"})
    @GetMapping
    public Result findAll() {
        try {
            return new Result(StatusCode.OK, true, "", adminService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 根据ID查询Admin
     *
     * @param adminId adminId
     * @return Result
     */
    @HasPermissions(values = {"ADMIN_SELECT"})
    @GetMapping("/{adminId}")
    public Result findById(@PathVariable int adminId) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("admin", adminService.findById(adminId));
            map.put("roleIds", adminService.findAdminRole(adminId));
            return new Result(StatusCode.OK, true, "", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 修改管理员
     *
     * @param roleIds roleIds
     * @param admin   admin
     * @return Result
     */
    @HasPermissions(values = {"ADMIN_UPDATE"})
    @PutMapping("/{roleIds}")
    public Result updateAdminRole(@PathVariable int[] roleIds, @RequestBody Admin admin) {
        try {
            admin.setLoginname(RSAUtil.decryptBase64(admin.getLoginname()));
            admin.setPassword(RSAUtil.decryptBase64(admin.getPassword()));
            adminService.editAdminRoles(admin, roleIds);
            return new Result(StatusCode.OK, true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "修改失败，可能是名称重复");
    }

    /**
     * 禁用管理员
     *
     * @param adminId adminId
     * @return Result
     */
    @HasPermissions(values = {"ADMIN_UPDATE"})
    @GetMapping("/dis/{adminId}")
    public Result disAdmin(@PathVariable int adminId) {
        try {
            adminService.disAdmin(adminId);
            return new Result(StatusCode.OK, true, "禁用成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "禁用失败");
    }

    /**
     * 恢复管理员
     *
     * @param adminId adminId
     * @return Result
     */
    @HasPermissions(values = {"ADMIN_UPDATE"})
    @GetMapping("/rec/{adminId}")
    public Result recAdmin(@PathVariable int adminId) {
        try {
            adminService.recAdmin(adminId);
            return new Result(StatusCode.OK, true, "恢复成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "恢复失败");
    }

    /**
     * 删除admin
     *
     * @param adminId adminId
     * @return Result
     */
    @HasPermissions(values = {"ADMIN_DELETE"})
    @DeleteMapping("/{adminId}")
    public Result remove(@PathVariable int adminId) {
        try {
            adminService.remove(adminId);
            return new Result(StatusCode.OK, true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "删除失败");
    }

    /**
     * 管理员登录
     *
     * @param map map
     * @return Result
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> map) {
        try {
            String uuid = map.get("uuid");
            String code = map.get("code");
            String loginname = map.get("loginname");
            String password = map.get("password");
            String verifyCode = redisTemplate.opsForValue().get("verifyCode_" + uuid);
            if (verifyCode == null) {
                return new Result(StatusCode.FAIL, false, "验证码过期");
            }
            if (!verifyCode.equals(code)) {
                return new Result(StatusCode.VERIFICATION_CODE_ERROR, false, "验证码错误");
            }
            loginname = RSAUtil.decryptBase64(loginname);
            password = RSAUtil.decryptBase64(password);
            Admin admin = adminService.login(loginname, password);
            if (admin != null) {
                //设置权限
                Set<String> adminPerMissions = adminService.findAdminPerMissions(admin.getId());
                for (String adminPerMission : adminPerMissions) {
                    redisTemplate.opsForSet().add("permissions_"+admin.getId(), adminPerMission);
                }
                //添加一个已登录权限
                redisTemplate.opsForSet().add("permissions_"+admin.getId(), "IS_LOGIN");
                //设置有效期
                redisTemplate.expire("permissions_"+admin.getId(),20,TimeUnit.HOURS);
                //生成token并返回给前端
                String token = jwtUtil.createJwt(String.valueOf(admin.getId()), admin.getLoginname(), "admin");
                //判断是不是运维
                if (admin.getLoginname().endsWith("_op")) {
                    redisTemplate.opsForSet().add("ADMIN_OPERATE", String.valueOf(admin.getId()));
                }
                return new Result(StatusCode.OK, true, "登录成功", token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        }
        return new Result(StatusCode.USER_OR_PASSWORD_ERROR, false, "账号或密码错误或者账号被禁用");
    }

    /**
     * 管理员分页
     *
     * @param pageV pageV
     * @return Result
     */
    @HasPermissions(values = {"ADMIN_SELECT"})
    @PostMapping("/search")
    public Result findByAdminPage(@RequestBody PageV pageV) {
        try {
            return new Result(StatusCode.OK, true, "", adminService.findByAdminPage(pageV.getQueryString(), pageV.getCurrentPage(), pageV.getPageSize()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 获取管理员信息
     *
     * @return Result
     */
    @HasPermissions(values = {"IS_LOGIN"})
    @GetMapping("/info")
    public Result getInfo() {
        try {
            String id = (String) request.getAttribute("id");
            Admin admin = adminService.findById(Integer.parseInt(id));
            if (admin == null) {
                return new Result(StatusCode.FAIL, false, "账号或密码错误");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("loginname", admin.getLoginname());
            map.put("id", admin.getId());
            return new Result(StatusCode.OK, true, "", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.OK, false, "获取管理员名失败");
    }

    /**
     * 获取菜单
     *
     * @param adminid adminid
     * @return Result
     */
    @HasPermissions(values = {"IS_LOGIN"})
    @GetMapping("/getMenu/{adminid}")
    public Result getMenu(@PathVariable Integer adminid) {
        try {
            return new Result(StatusCode.OK, true, "", adminService.getMenu(adminid));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(StatusCode.FAIL, false, "获取菜单失败");
    }


    /**
     * 登出
     *
     * @return Result
     */
    @HasPermissions(values = {"IS_LOGIN"})
    @GetMapping("/logout/{id}")
    public Result logout(@PathVariable Integer id) {
        try {
            redisTemplate.opsForSet().remove("ADMIN_OPERATE", String.valueOf(id));
            redisTemplate.delete("permissions_"+id);
            return new Result(StatusCode.OK, true, "注销成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        }
        return new Result(StatusCode.FAIL, false, "发生错误，未成功注销");
    }


}
