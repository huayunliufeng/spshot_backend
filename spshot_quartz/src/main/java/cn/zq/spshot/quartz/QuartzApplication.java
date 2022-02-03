package cn.zq.spshot.quartz;

import cn.zq.spshot.common.utils.FtpUtil;
import cn.zq.spshot.common.utils.WeiXinUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author 华韵流风
 * @ClassName QuartzApplication
 * @Date 2022/1/7 15:53
 * @packageName cn.zq.spshot.quartz
 * @Description TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class QuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class);
    }

    @Bean
    public FtpUtil ftpUtil() {
        return new FtpUtil();
    }

    @Bean
    public WeiXinUtil weiXinUtil() {
        return new WeiXinUtil();
    }

}
