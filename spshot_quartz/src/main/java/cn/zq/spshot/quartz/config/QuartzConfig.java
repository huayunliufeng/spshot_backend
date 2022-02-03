package cn.zq.spshot.quartz.config;

import cn.zq.spshot.quartz.job.DeletePicAndRecNum;
import cn.zq.spshot.quartz.job.GetAccessToken;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 华韵流风
 * @ClassName QuartzConfig
 * @Date 2022/1/7 17:17
 * @packageName cn.zq.spshot.quartz.config
 * @Description TODO
 */
@Configuration
public class QuartzConfig {

//    @Bean
//    public JobDetail jobDetail1() {
//        return JobBuilder.newJob(DeletePicAndRecNum.class)
//                .withIdentity("01", "01")
//                .storeDurably()
//                .build();
//    }
//
//    @Bean
//    public JobDetail jobDetail2() {
//        return JobBuilder.newJob(GetAccessToken.class)
//                .withIdentity("02", "02")
//                .storeDurably()
//                .build();
//    }
//
//    /**
//     * 启动时获取一次access_token
//     *
//     * @return JobDetail
//     */
//    @Bean
//    public JobDetail jobDetail3() {
//        return JobBuilder.newJob(GetAccessToken.class)
//                .withIdentity("03", "03")
//                .storeDurably()
//                .build();
//    }
//
//    @Bean
//    public Trigger trigger1() {
//        return TriggerBuilder.newTrigger()
//                .forJob(jobDetail1())
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 3 */1 * ?"))
//                .build();
//    }
//
//    @Bean
//    public Trigger trigger2() {
//        return TriggerBuilder.newTrigger()
//                .forJob(jobDetail2())
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0/2 * * ?")).startNow()
//                .build();
//    }
//
//    /**
//     * 启动时获取一次access_token
//     *
//     * @return Trigger
//     */
//    @Bean
//    public Trigger trigger3() {
//        return TriggerBuilder.newTrigger()
//                .forJob(jobDetail3())
//                .startNow()
//                .build();
//    }

}
