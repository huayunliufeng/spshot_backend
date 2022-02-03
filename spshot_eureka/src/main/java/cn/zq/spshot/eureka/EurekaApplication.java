package cn.zq.spshot.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 华韵流风
 * @ClassName EurekaApplication
 * @Date 2021/11/27 20:56
 * @packageName cn.zq.spshot.eureka
 * @Description TODO
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class);
    }

}
