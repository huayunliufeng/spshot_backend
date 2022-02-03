package cn.zq.spshot.quartz.job;

import cn.zq.spshot.common.utils.FtpUtil;
import cn.zq.spshot.quartz.client.DataClient;
import cn.zq.spshot.quartz.client.IssureClient;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author 华韵流风
 * @ClassName DeletePic
 * @Date 2022/1/7 16:54
 * @packageName cn.zq.spshot.quartz.job
 * @Description TODO
 */
@Component
public class DeletePicAndRecNum extends QuartzJobBean {

    @Resource
    DataClient dataClient;

    @Resource
    IssureClient issureClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private FtpUtil ftpUtil;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            //重置发布次数
            if (issureClient.recTodayNum().getFlag().equals(Boolean.TRUE)) {
                System.out.println(date + " 重置成功");
            } else {
                System.out.println(date + " 重置失败");
            }
            //清除冗余文件
            List<String> images = (List<String>) dataClient.getAllImages().getData();
            for (String image : images) {
                redisTemplate.opsForSet().add("mysqlImgData", image);
            }
            Set<String> difference = redisTemplate.opsForSet().difference("redisImgData", "mysqlImgData");
            if (difference.size() > 0 && difference != null) {
                boolean[] result = ftpUtil.deleteFile("images", difference);
                int i = 0;
                for (String s : difference) {
                    redisTemplate.opsForSet().remove("redisImgData", s);
                    if (result[i++]) {
                        System.out.println(date + " " + s + " 删除成功");
                    } else {
                        System.out.println(date + " " + s + " 删除失败");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        }
    }
}
