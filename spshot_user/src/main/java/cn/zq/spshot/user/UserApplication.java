package cn.zq.spshot.user;

import cn.zq.spshot.common.utils.JwtUtil;
import cn.zq.spshot.common.utils.WeiXinUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * @author 华韵流风
 * @ClassName UserApplication
 * @Date 2021/11/27 21:41
 * @packageName cn.zq.spshot.user
 * @Description TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "cn.zq.spshot.user.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    public WeiXinUtil weiXinUtil(){
        return new WeiXinUtil();
    }


}
