package cn.zq.spshot.issure.controller;

import cn.zq.spshot.common.aop.HasPermissions;
import cn.zq.spshot.common.entity.PageResult;
import cn.zq.spshot.common.entity.PageV;
import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.common.entity.StatusCode;
import cn.zq.spshot.issure.pojo.Choose;
import cn.zq.spshot.issure.pojo.Issure;
import cn.zq.spshot.issure.pojo.Item;
import cn.zq.spshot.issure.pojo.Items;
import cn.zq.spshot.issure.service.IssureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Created on 2021/12/7
 *
 * @author 华韵流风
 */
@RestController
@RefreshScope
//@CrossOrigin
@RequestMapping("/issure")
public class IssureController {

    @Autowired
    private IssureService issureService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 根据id查询issure
     *
     * @param issureid issureid
     * @return Result
     */
    @GetMapping("/{issureid}")
    public Result getIssureById(@PathVariable Integer issureid) {

        try {
            Map<String, Object> map = new HashMap<>();
            //id
            map.put("id", issureid);
            //标题
            Issure issure = issureService.findById(issureid);
            map.put("title", issure.getTitle());
            //发布者
            map.put("username", issure.getUsername());
            // 参与密码
            map.put("partpass", issure.getPartpass());
            //选项及问题
            map.put("items", issure.getItems());
            return new Result(StatusCode.OK, true, "", map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 设置过期
     *
     * @param id id
     * @return Result
     */
    @GetMapping("/setIssureExpire/{id}")
    public Result setIssureExpire(@PathVariable Integer id) {
        try {
            issureService.setIssureExpire(id);
            return new Result(StatusCode.OK, true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "操作失败");
    }

    /**
     * 根据issureid获取用户openid
     *
     * @param issureid issureid
     * @return Result
     */
    @GetMapping("/getOpenId/{issureid}")
    public Result getOpenId(@PathVariable Integer issureid) {
        try {
            return new Result(StatusCode.OK, true, "", issureService.getOpenId(issureid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }


    /**
     * 审核记录分页
     *
     * @param pageV pageV
     * @return Result
     */
    @HasPermissions(values = {"ISSURE_SELECT"})
    @PostMapping("/record/search")
    public Result findByVerifyPage(@RequestBody PageV pageV) {
        try {
            return new Result(StatusCode.OK, true, "", issureService.findVerifyByKeyword(pageV.getQueryString(), pageV.getCurrentPage(), pageV.getPageSize()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 查询已通过且公开的投票
     *
     * @param pageV pageV
     * @return Result
     */
    @PostMapping("/search")
    public Result findPublicAndAccessIssure(@RequestBody PageV pageV) {
        try {
            return new Result(StatusCode.OK, true, "", issureService.findPublicAndAccessIssure(pageV.getQueryString(), pageV.getCurrentPage(), pageV.getPageSize()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 查询公开和未公开的投票
     *
     * @param pageV pageV
     * @return Result
     */
    @PostMapping("/search/isNotPublic")
    public Result findIsNotPublic(@RequestBody PageV pageV) {
        try {
            if ("".equals(pageV.getQueryString())) {
                return new Result(StatusCode.OK, true, "", issureService.findPublicAndAccessIssure(pageV.getQueryString(), pageV.getCurrentPage(), pageV.getPageSize()));
            }
            return new Result(StatusCode.OK, true, "", issureService.findIsNotPublic(pageV.getQueryString(), pageV.getCurrentPage(), pageV.getPageSize()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 发布投票分页（所有）
     *
     * @param pageV pageV
     * @param state state
     * @return Result
     */
    @HasPermissions(values = {"ISSURE_SELECT"})
    @PostMapping("/search/{state}")
    public Result findByIssurePage(@RequestBody PageV pageV, @PathVariable int state) {
        try {
            return new Result(StatusCode.OK, true, "", issureService.findIssureByKeyWord(pageV.getQueryString(), state, pageV.getCurrentPage(), pageV.getPageSize()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 发布投票分页（运维专属）
     *
     * @param pageV pageV
     * @param state state
     * @return Result
     */
    @HasPermissions(values = {"ISSURE_SELECT"})
    @PostMapping("/search/{state}/{adminid}")
    public Result findByIssurePageOP(@RequestBody PageV pageV, @PathVariable int state, @PathVariable int adminid) {
        try {
            return new Result(StatusCode.OK, true, "", issureService.findIssureByKeyWordOP(pageV.getQueryString(), state, adminid, pageV.getCurrentPage(), pageV.getPageSize()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 通过审核的投票
     *
     * @param pageV pageV
     * @param state state
     * @return Result
     */
    @HasPermissions(values = {"ISSURE_SELECT"})
    @PostMapping("/pass/{state}/{isexpire}")
    public Result findByIssureOngoingPage(@RequestBody PageV pageV, @PathVariable int state, @PathVariable Integer isexpire) {
        try {
            return new Result(StatusCode.OK, true, "", issureService.findIssureByKeyWordOngoing(pageV.getQueryString(), state, isexpire, pageV.getCurrentPage(), pageV.getPageSize()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 查询未审核数
     *
     * @return Result
     */
    @HasPermissions(values = {"ISSURE_SELECT"})
    @GetMapping("/count/{adminid}")
    public Result findUnVerifyCount(@PathVariable Integer adminid) {

        try {
            return new Result(StatusCode.OK, true, "", issureService.findUnVerifyCount(adminid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }


    /**
     * 改变投票状态
     *
     * @param map   map
     * @param state state
     * @return Result
     */
    @HasPermissions(values = {"ISSURE_UPDATE"})
    @PostMapping("/audit/{state}")
    public Result changeState(@PathVariable Integer state, @RequestBody Map<String, Object> map) {

        try {
            String adminid = redisTemplate.opsForValue().get("issure_controller_" + map.get("issureid"));
            if (adminid == null || "".equals(adminid)) {
                //设置锁
                redisTemplate.opsForValue().set("issure_controller_" + map.get("issureid"), (String) map.get("adminid"), 5, TimeUnit.MINUTES);
            } else if (!adminid.equals(map.get("adminid"))) {
                return new Result(StatusCode.FAIL, false, "有其他管理员正在操作，请等待五分钟后操作");
            }
            issureService.changState(state, map);
            return new Result(StatusCode.OK, true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        }
        return new Result(StatusCode.FAIL, false, "操作失败");
    }

    /**
     * 查询投票详细信息
     *
     * @param issureid issureid
     * @return Result
     */
    @GetMapping("/desc/{issureid}")
    public Result getIssureDesc(@PathVariable Integer issureid) {

        try {
            Issure issure = issureService.findById(issureid);
            Map<String, Object> map = new HashMap<>();
            //基本信息
            Map<String, Object> desc = new HashMap<>();
            desc.put("id", issure.getId());
            desc.put("username", issure.getUsername());
            desc.put("title", issure.getTitle());
            desc.put("items", issure.getItems());
            map.put("desc", desc);

            //具体数据
            List<List<Map<String, Object>>> data = new ArrayList<>();
            for (Items item : issure.getItems()) {
                List<Map<String, Object>> tmp = new ArrayList<>();
                for (Item item1 : item.getItem()) {
                    Map<String, Object> ans = new HashMap<>();
                    ans.put("a", item1.getAns());
                    ans.put("value", issureService.countChange(item.getId(), item1.getAns()));
                    tmp.add(ans);
                }
                data.add(tmp);
            }
            map.put("data", data);
            return new Result(StatusCode.OK, true, "", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 客户端查询投票结果
     *
     * @param issureid issureid
     * @return Result
     */
    @GetMapping("/desc/res/{issureid}")
    public Result getIssureDescUser(@PathVariable Integer issureid) {
        try {
            Issure issure = issureService.findById(issureid);
            Map<String, Object> map = new HashMap<>();
            //基本信息
            map.put("id", issure.getId());
            map.put("username", issure.getUsername());
            map.put("title", issure.getTitle());
            //具体数据
            List<Map<String, Object>> itemsList = new ArrayList<>();
            for (Items item : issure.getItems()) {
                Map<String, Object> items = new HashMap<>();
                items.put("id", item.getId());
                items.put("question", item.getQuestion());
                items.put("optional", item.getOptional());
                long max = 0;
                List<Map<String, Object>> itemList = new ArrayList<>();
                for (Item item1 : item.getItem()) {
                    Map<String, Object> it = new HashMap<>();
                    it.put("ans", item1.getAns());
                    long num = issureService.countChange(item.getId(), item1.getAns());
                    it.put("num", num);
                    max = Math.max(max, num);
                    itemList.add(it);
                }
                items.put("max", max);
                items.put("item", itemList);
                itemsList.add(items);
            }
            map.put("items", itemsList);
            return new Result(StatusCode.OK, true, "", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }


    /**
     * 删除issure
     *
     * @param issureid issureid
     * @return Result
     */
    @HasPermissions(values = {"ISSURE_DELETE"})
    @DeleteMapping("/{issureid}")
    public Result deleteIssureById(@PathVariable Integer issureid) {
        try {
            //设置锁
            Boolean bool = redisTemplate.opsForValue().setIfAbsent("ISSURE_CONTROLLER_" + issureid, "YES", 10, TimeUnit.MINUTES);
            assert bool != null;
            if (!bool.equals(Boolean.TRUE)) {
                return new Result(StatusCode.FAIL, false, "有其他管理员正在操作");
            }
            issureService.delete(issureid);
            return new Result(StatusCode.OK, true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        }

        return new Result(StatusCode.FAIL, false, "删除失败");
    }


    /**
     * 添加issure
     *
     * @param issure issure
     * @return Result
     */
    @PostMapping
    public Result addIssure(@RequestBody Issure issure) {

        try {
            issureService.add(issure);
            return new Result(StatusCode.OK, true, "添加成功，等待审核");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "添加失败");
    }

    /**
     * 修改issure
     *
     * @param issure issure
     * @return Result
     */
    @HasPermissions(values = {"ISSURE_UPDATE"})
    @PutMapping
    public Result update(@RequestBody Issure issure) {
        try {
            issureService.update(issure);
            return new Result(StatusCode.OK, true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "修改失败");
    }


    /**
     * 参与投票
     *
     * @param userid  userid
     * @param chooses chooses
     * @return Result
     */
    @PostMapping("/part/{userid}/{issureid}")
    public Result part(@PathVariable Integer userid, @PathVariable Integer issureid, @RequestBody List<Choose> chooses) {

        try {
            Integer maxPerson = issureService.findById(issureid).getMaxperson();
            Integer currPerson = issureService.getIssureCurrPerson(issureid);
            if (maxPerson == -1 || currPerson < maxPerson) {
                issureService.part(chooses, userid, issureid);
                return new Result(StatusCode.OK, true, "成功参与");
            } else {
                return new Result(StatusCode.FAIL, false, "已达人数上限");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "参与失败");
    }

    /**
     * 查询用户投票详情
     *
     * @param issureid issureid
     * @param userid   userid
     * @return Result
     */
    @GetMapping("/desc/{issureid}/{userid}")
    public Result findUserIssureChoose(@PathVariable Integer issureid, @PathVariable Integer userid) {
        try {
            return new Result(StatusCode.OK, true, "", issureService.findUserIssureChoose(issureid, userid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 查询热门投票
     *
     * @return Result
     */
    @GetMapping("/hotissure")
    public Result getHotIssure() {
        try {
            return new Result(StatusCode.OK, true, "", issureService.getHotIssure());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }

    /**
     * 重置每日发布数
     *
     * @return Result
     */
    @GetMapping("/recTodayNum")
    public Result recTodayNum() {
        try {
            issureService.recTodayNum();
            return new Result(StatusCode.OK, true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "操作失败");
    }

    /**
     * 查询用户发布的投票
     *
     * @param userid userid
     * @param type   type
     * @return Result
     */
    @PostMapping("/show/issure/{userid}/{type}")
    public Result getIssureType(@PathVariable Integer userid, @PathVariable Integer type, @RequestBody PageV pageV) {
        try {
            return new Result(StatusCode.OK, true, "", issureService.getIssureType(pageV.getQueryString(), type, userid, pageV.getCurrentPage(), pageV.getPageSize()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");

    }

    /**
     * 查询用户参与的投票
     *
     * @param userid userid
     * @param type   type
     * @param pageV  pageV
     * @return Result
     */
    @PostMapping("/userpart/{userid}/{type}")
    public Result getUserPart(@PathVariable Integer userid, @PathVariable Integer type, @RequestBody PageV pageV) {
        try {
            PageResult<Issure> userPart = issureService.getUserPart(userid, type, pageV.getQueryString(), pageV.getCurrentPage(), pageV.getPageSize());
            if (userPart == null) {
                return new Result(StatusCode.OK, true, "暂无参加记录");
            }
            return new Result(StatusCode.OK, true, "", userPart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(StatusCode.FAIL, false, "查询失败");
    }
}
