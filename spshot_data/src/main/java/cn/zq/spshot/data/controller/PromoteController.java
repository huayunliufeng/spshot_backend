package cn.zq.spshot.data.controller;

import cn.zq.spshot.common.aop.HasPermissions;
import cn.zq.spshot.common.entity.PageV;
import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.common.entity.StatusCode;
import cn.zq.spshot.common.utils.FtpUtil;
import cn.zq.spshot.data.pojo.Promote;
import cn.zq.spshot.data.service.PromoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

/**
 * Created on 2021/12/13
 *
 * @author 华韵流风
 */
@RestController
@RequestMapping("/promote")
@RefreshScope
//@CrossOrigin
public class PromoteController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private FtpUtil ftpUtil;

    @Autowired
    private PromoteService promoteService;

    /**
     * 根据id查询promote
     *
     * @param id id
     * @return Result
     */
    @HasPermissions(values = {"PROMOTE_SELECT"})
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        try {
            return new Result(StatusCode.OK, true, "", promoteService.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 获取所有图片
     *
     * @return Result
     */
    @GetMapping("/getAllImages")
    public Result getAllImages() {
        try {
            return new Result(StatusCode.OK, true, "查询成功", promoteService.getAllImages());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }


    /**
     * 模糊查询
     *
     * @param pageV pageV
     * @return Result
     */
    @HasPermissions(values = {"PROMOTE_SELECT"})
    @PostMapping("/search")
    public Result findAllPage(@RequestBody PageV pageV) {
        try {
            return new Result(StatusCode.OK, true, "", promoteService.findByKeyWord(pageV.getQueryString(), pageV.getCurrentPage(), pageV.getPageSize()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 新增promote
     *
     * @param promote promote
     * @return Result
     */
    @HasPermissions(values = {"PROMOTE_INSERT"})
    @PostMapping
    public Result add(@RequestBody Promote promote) {

        try {
            promoteService.add(promote);
            return new Result(StatusCode.OK, true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "添加失败");
    }


    /**
     * 修改promote
     *
     * @param promote promote
     * @return Result
     */
    @HasPermissions(values = {"PROMOTE_UPDATE"})
    @PutMapping
    public Result update(@RequestBody Promote promote) {
        try {
            promoteService.update(promote);
            return new Result(StatusCode.OK, true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "修改失败");
    }

    /**
     * 删除promote
     *
     * @param id id
     * @return Result
     */
    @HasPermissions(values = {"PROMOTE_DELETE"})
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            promoteService.delete(id);
            return new Result(StatusCode.OK, true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "删除失败");
    }

    /**
     * 改变状态
     *
     * @param id    id
     * @param state state
     * @return Result
     */
    @HasPermissions(values = {"PROMOTE_UPDATE"})
    @GetMapping("/{id}/{state}")
    public Result setState(@PathVariable Integer id, @PathVariable Integer state) {
        try {
            promoteService.setState(id, state);
            return new Result(StatusCode.OK, true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "操作失败");
    }

    /**
     * 查询上架推广总数，不超过6个
     *
     * @return Result
     */
    @GetMapping("/count")
    public Result countRec() {
        try {
            return new Result(StatusCode.OK, true, "", promoteService.countRec());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 获取上架的推广
     *
     * @return Result
     */
    @GetMapping("/getRecPromote")
    public Result getRecPromote() {
        try {
            return new Result(StatusCode.OK, true, "", promoteService.getRecPromote());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 文件上传
     *
     * @param img img
     * @return Result
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile img) {
        String imgFileName = img.getOriginalFilename();
        int index = imgFileName.lastIndexOf(".");
        String suffix = imgFileName.substring(index);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = uuid + suffix;
        try {
            boolean res = ftpUtil.uploadFile("images", fileName, img.getInputStream());
            redisTemplate.opsForSet().add("redisImgData", fileName);
            if (res) {
                return new Result(StatusCode.OK, true, "上传成功", fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        }
        return new Result(StatusCode.FAIL, false, "上传失败");
    }

}
