package cn.zq.spshot.issure;

import cn.zq.spshot.common.utils.FtpUtil;
import cn.zq.spshot.common.utils.JwtUtil;
import cn.zq.spshot.common.utils.WeiXinUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author 华韵流风
 * @ClassName IssureController
 * @Date 2021/12/18 12:39
 * @packageName cn.zq.spshot.issure
 * @Description TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = "cn.zq.spshot.issure.mapper")
public class IssureApplication {
    public static void main(String[] args) {
        SpringApplication.run(IssureApplication.class);
    }

    @Bean
    public FtpUtil ftpUtil() {
        return new FtpUtil();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    public WeiXinUtil weiXinUtil() {
        return new WeiXinUtil();
    }

}
