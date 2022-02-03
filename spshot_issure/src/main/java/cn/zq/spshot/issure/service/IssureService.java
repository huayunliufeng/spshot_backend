package cn.zq.spshot.issure.service;

import cn.zq.spshot.common.entity.PageResult;
import cn.zq.spshot.common.entity.TemplateData;
import cn.zq.spshot.common.entity.WeiXinMessage;
import cn.zq.spshot.common.utils.ThreadUtil;
import cn.zq.spshot.common.utils.WeiXinUtil;
import cn.zq.spshot.issure.client.QuartzClient;
import cn.zq.spshot.issure.mapper.IssureDao;
import cn.zq.spshot.issure.pojo.Choose;
import cn.zq.spshot.issure.pojo.Issure;
import cn.zq.spshot.issure.pojo.Items;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2021/12/13
 *
 * @author 华韵流风
 */
@Service
public class IssureService {

    @Autowired
    private IssureDao issureDao;

    @Autowired
    private ItemsService itemsService;

//    @Autowired
//    private TemplateEngine templateEngine;
//
//    @Autowired
//    private FtpUtil ftpUtil;

    @Autowired
    private WeiXinUtil weiXinUtil;

    @Resource
    private QuartzClient quartzClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 新增issure
     *
     * @param issure issure
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(Issure issure) {
        issure.setCreatetime(Calendar.getInstance().getTime());
        String adminid = redisTemplate.opsForSet().randomMember("ADMIN_OPERATE");
        if ("".equals(adminid) || adminid == null) {
            adminid = "1";
        }
        issure.setAdminid(Integer.valueOf(adminid));
        issureDao.add(issure);
        //新增问题
        itemsService.add(issure.getItems());
        //新增记录
        for (Items item : issure.getItems()) {
            issureDao.addIssureItem(issure.getId(), item.getId());
        }
        //今日发布数减一
        issureDao.setTodayNum(issure.getUserid());
        //总发布数加一
        issureDao.setTotalIssure(issure.getUserid());
        //释放redis连接
        RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
    }

    /**
     * 查询热门投票
     *
     * @return List<Map < String, Object>>
     */
    public List<Map<String, Object>> getHotIssure() {
        return issureDao.getHotIssure();
    }

    /**
     * 参与投票
     *
     * @param chooses  chooses
     * @param id       id
     * @param issureid issureid
     */
    @Transactional(rollbackFor = Exception.class)
    public void part(List<Choose> chooses, Integer id, Integer issureid) throws Exception {
        //设置选项
        itemsService.part(chooses);
        //该用户总参与数加一
        issureDao.setTotalPart(id);
        //当前参与人数加一
        issureDao.setCurrPerson(issureid);
        //新增参与记录
        Map<String, Object> map = new HashMap<>();
        map.put("userid", id);
        map.put("issureid", issureid);
        map.put("parttime", Calendar.getInstance().getTime());
        issureDao.addPart(map);
        //如果达到最大人数
        Integer maxPerson = issureDao.findById(issureid).getMaxperson();
        Integer currPerson = issureDao.getIssureCurrPerson(issureid);
        if (currPerson >= maxPerson && maxPerson != -1) {
            redisTemplate.opsForValue().set("issureMaxPerson_" + issureid, "1");
            //设置过期
            SetIssureState(issureid);
        }
    }

    /**
     * 查询用户投票信息
     *
     * @param issureid issureid
     * @param userid   userid
     * @return List<List < String>>
     */
    public List<List<String>> findUserIssureChoose(Integer issureid, Integer userid) {
        List<Integer> issureItems = issureDao.findIssureItems(issureid);
        List<List<String>> choose = new ArrayList<>();
        for (Integer issureItem : issureItems) {
            choose.add(itemsService.findUserChoose(userid, issureItem));
        }
        return choose;
    }

    /**
     * 新增verify
     *
     * @param map map
     */
    @Transactional(rollbackFor = Exception.class)
    public void addVerify(Map<String, Object> map) {
        issureDao.addVerify(map);
    }


    /**
     * 更新issure
     *
     * @param issure issure
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Issure issure) {
        //更新问题
        itemsService.update(issure.getItems());
        //更新发布
        issureDao.update(issure);
    }

    /**
     * 删除issure
     *
     * @param id id
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        issureDao.delete(id);
    }

    /**
     * 根据id查询issure
     *
     * @param id id
     * @return Issure
     */
    public Issure findById(Integer id) {
        Issure issure = issureDao.findById(id);
        List<Integer> issureItems = findIssureItems(id);
        List<Items> list = new ArrayList<>();
        for (Integer issureItem : issureItems) {
            Items items = itemsService.findById(issureItem);
            list.add(items);
        }
        issure.setItems(list);
        return issure;
    }

    /**
     * 根据id查询issure的items
     *
     * @param id id
     * @return List<Integer>
     */
    private List<Integer> findIssureItems(Integer id) {
        return issureDao.findIssureItems(id);
    }


    /**
     * 查询issure并分页（所有）
     *
     * @param keyWord keyWord
     * @param state   state
     * @param page    page
     * @param size    size
     * @return PageResult
     */
    public PageResult<Issure> findIssureByKeyWord(String keyWord, Integer state, int page, int size) {
        PageHelper.startPage(page, size);
        Page<Issure> pageList = (Page<Issure>) issureDao.findIssureByKeyWord(keyWord, state);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 查询已通过且公开的投票
     *
     * @param keyWord keyWord
     * @param page    page
     * @param size    size
     * @return PageResult
     */
    public PageResult<Issure> findPublicAndAccessIssure(String keyWord, int page, int size) {
        PageHelper.startPage(page, size);
        Page<Issure> pageList = (Page<Issure>) issureDao.findPublicAndAccessIssure(keyWord);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 查询已通过且公开的投票
     *
     * @param keyWord keyWord
     * @param page    page
     * @param size    size
     * @return PageResult
     */
    public PageResult<Issure> findIsNotPublic(String keyWord, int page, int size) {
        PageHelper.startPage(page, size);
        Page<Issure> pageList = (Page<Issure>) issureDao.findIsNotPublic(keyWord);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }


    /**
     * 查询issure并分页（运维专属）
     *
     * @param keyWord keyWord
     * @param state   state
     * @param adminid adminid
     * @param page    page
     * @param size    size
     * @return PageResult
     */
    public PageResult<Issure> findIssureByKeyWordOP(String keyWord, Integer state, Integer adminid, int page, int size) {
        PageHelper.startPage(page, size);
        Page<Issure> pageList = (Page<Issure>) issureDao.findIssureByKeyWordOP(keyWord, state, adminid);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }


    /**
     * 查询通过的issure并分页
     *
     * @return List<Issure>
     */
    public PageResult<Issure> findIssureByKeyWordOngoing(String keyWord, Integer state, Integer isexpire, int page, int size) {
        PageHelper.startPage(page, size);
        Page<Issure> pageList = (Page<Issure>) issureDao.findIssureByKeyWordOngoing(keyWord, state, isexpire);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }


    /**
     * 查询审核记录
     *
     * @param keyWord keyWord
     * @param page    page
     * @param size    size
     * @return PageResult
     */
    public PageResult<Map<String, Object>> findVerifyByKeyword(String keyWord, int page, int size) {
        PageHelper.startPage(page, size);
        Page<Map<String, Object>> pageList = (Page<Map<String, Object>>) issureDao.findVerifyByKeyword(keyWord);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 查询未审核数
     *
     * @param adminid adminid
     * @return Integer
     */
    public Integer findUnVerifyCount(Integer adminid) {
        return issureDao.findUnVerifyCount(adminid);
    }


    /**
     * 改变审核状态：0 未审核，1 通过，-1 未通过，2 封禁
     *
     * @param state state
     * @param map   map
     */
    @Transactional(rollbackFor = Exception.class)
    public void changState(Integer state, Map<String, Object> map) throws Exception {
        Integer id = (Integer) map.get("issureid");
        issureDao.changState(id, state);
        Date accepttime = Calendar.getInstance().getTime();
        map.put("accepttime", accepttime);
        map.put("state", state);
        addVerify(map);
        issureDao.setAccepttime(id, accepttime);
        //发送通知
        String access_token = redisTemplate.opsForValue().get("access_token");
        Map<String, Object> data = new HashMap<>();
        String openid = issureDao.getOpenIdByIssureId(id);
        data.put("thing2", new TemplateData(issureDao.findById(id).getTitle()));
        data.put("date7", new TemplateData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
        if (state == 1) {
            String tmp = redisTemplate.opsForValue().get("issureMaxPerson_" + id);
            Integer effecttime = issureDao.findById(id).getEffecttime();
            //是否是解封
            if (!"0".equals(tmp)) {
                //页面静态化
                //wxmlStatic(findById(id));
                //设置参与码
                String partCode = getPartCode(4);
                issureDao.setPartCode(id, partCode);
                //设置是否达到最大人数
                redisTemplate.opsForValue().set("issureMaxPerson_" + id, "0", effecttime, TimeUnit.HOURS);
                //开始倒计时
                ThreadUtil.pool.execute(() -> System.out.println(quartzClient.setState(id, effecttime)));
            }
            //给客户端发送通过成功通知
            data.put("phrase1", new TemplateData("通过审核"));
            data.put("thing3", new TemplateData("符合规范"));
            data.put("character_string20", new TemplateData(issureDao.findById(id).getPartcode()));
        } else if (state == -1) {
            //给客户端发送未通过通知
            data.put("phrase1", new TemplateData("未通过审核"));
            data.put("thing3", new TemplateData((String) map.get("reason")));
            data.put("character_string20", new TemplateData("null"));
        } else {
            //给客户端发送封禁通知
            data.put("phrase1", new TemplateData("封禁"));
            data.put("thing3", new TemplateData((String) map.get("reason")));
            data.put("character_string20", new TemplateData("null"));
        }
        WeiXinMessage weiXinMessage = new WeiXinMessage(access_token, openid, "", "", data);
//        Map message = weiXinUtil.pushMessage(weiXinMessage);
//        System.out.println(message);
        //释放redis连接
        RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
    }

    /**
     * 重置每日发布数
     */
    @Transactional(rollbackFor = Exception.class)
    public void recTodayNum() {
        issureDao.recTodayNum();
    }

    /**
     * 设置过期
     *
     * @param id id
     */
    @Transactional(rollbackFor = Exception.class)
    public void setIssureExpire(Integer id) {
        issureDao.setIssureExpire(id);
    }

    /**
     * 获取用户id
     *
     * @param issureid issureid
     * @return List<Integer>
     */
    public Set<String> getOpenId(Integer issureid) {
        List<Integer> userids = new ArrayList<>();
        Set<String> openids = new HashSet<>();
        userids.add(issureDao.getUserIdFromIssure(issureid));
        userids.addAll(issureDao.getUserIdFromPart(issureid));
        for (Integer userid : userids) {
            openids.add(issureDao.getOpenId(userid));
        }
        return openids;
    }


    /**
     * 统计选项人数
     *
     * @param itemsid itemsid
     * @param choose  choose
     * @return long
     */
    public long countChange(Integer itemsid, String choose) {
        return itemsService.countChange(itemsid, choose);
    }

    /**
     * 获取用户发布的投票
     *
     * @param keyWord keyWord
     * @param type    type
     * @param userid  userid
     * @param page    page
     * @param size    size
     * @return PageResult<Issure>
     */
    public PageResult<Issure> getIssureType(String keyWord, Integer type, Integer userid, int page, int size) {
        PageHelper.startPage(page, size);
        Page<Issure> pageList = (Page<Issure>) issureDao.getIssureType(userid, type, keyWord);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 获取用户参与的投票
     *
     * @param userid  userid
     * @param type    type
     * @param keyWord keyWord
     * @param page    page
     * @param size    size
     * @return PageResult<Issure>
     */
    public PageResult<Issure> getUserPart(Integer userid, Integer type, String keyWord, int page, int size) {
        List<Integer> ids = issureDao.getUserPart(userid);
        if (ids.size() <= 0) {
            return null;
        }
        PageHelper.startPage(page, size);
        Page<Issure> pageList = (Page<Issure>) issureDao.findUserPartIssureByType(ids, type, keyWord);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 生成参与码
     *
     * @param length length
     * @return String
     */
    private String getPartCode(int length) {
        String str = "0123456789qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM";
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < length; i++) {
            buff.append(str.charAt(new Random().nextInt(str.length())));
        }
        return buff.toString();
    }

    /**
     * 获取指定投票当前参与人数
     *
     * @param issureid issureid
     * @return Integer
     */
    public Integer getIssureCurrPerson(Integer issureid) {
        return issureDao.getIssureCurrPerson(issureid);
    }

    /**
     * 设置过期
     *
     * @param id id
     * @throws Exception Exception
     */
    public void SetIssureState(Integer id) throws Exception {
        issureDao.setIssureExpire(id);
        // 获取openid
        Set<String> openIds = getOpenId(id);
        //发送消息
        String access_token = redisTemplate.opsForValue().get("access_token");
        Map<String, Object> map = new HashMap<>();
        map.put("thing1", new TemplateData(issureDao.findById(id).getTitle()));
        map.put("phrase2", new TemplateData("点击查看"));
        map.put("time3", new TemplateData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance(TimeZone.getTimeZone("CCT")).getTime())));
        for (String openId : openIds) {
            WeiXinMessage weiXinMessage = new WeiXinMessage(access_token, openId, "", "" + id, map);
//            Map<String, Object> map1 = (Map<String, Object>) weiXinUtil.pushMessage(weiXinMessage);
//            System.out.println(map1);
        }
        RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
    }

//    /**
//     * 页面静态化
//     *
//     * @param issure issure
//     * @return boolean
//     */
//    private boolean wxmlStatic(Issure issure) {
//        //页面静态化
//        //上下文对象，用于存放模板所需要的数据
//        Context context = new Context();
//        //存放数据
//        context.setVariable("hello", issure.getId());
//        //通过engine模板引擎写入本地文件
//        //如果需要修改默认的前后缀，需要自己定义模板解析器
////        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
////        templateResolver.setPrefix("templates");
////        templateResolver.setSuffix(".html");
////        templateEngine.setTemplateResolver(templateResolver);
//        //参数：1.模板（默认前缀 templates 默认后缀 .html）2.context上下文对象 3.输入流，需要写出的位置
//        ByteArrayOutputStream output = null;
//        PrintWriter writer = null;
//        ByteArrayInputStream input = null;
//        try {
//            output = new ByteArrayOutputStream();
//            writer = new PrintWriter(output);
//            templateEngine.process("index", context, writer);
//            input = new ByteArrayInputStream(output.toByteArray());
//            return ftpUtil.uploadFile("html", "issure_" + issure.getId() + ".html", input);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                assert input != null;
//                input.close();
//                writer.close();
//                output.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }


}
