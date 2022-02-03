package cn.zq.spshot.data;

import cn.zq.spshot.common.utils.FtpUtil;
import cn.zq.spshot.common.utils.JwtUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author 华韵流风
 * @ClassName DataApplication
 * @Date 2021/12/18 13:03
 * @packageName cn.zq.spshot.data
 * @Description TODO
 */
@SpringCloudApplication
@MapperScan(basePackages = "cn.zq.spshot.data.mapper")
@EnableFeignClients
public class DataApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class);
    }

    @Bean
    public FtpUtil ftpUtil() {
        return new FtpUtil();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
