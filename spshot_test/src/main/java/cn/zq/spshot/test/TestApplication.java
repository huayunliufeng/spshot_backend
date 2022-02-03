package cn.zq.spshot.test;

import cn.zq.spshot.common.utils.WeiXinUtil;
import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author 华韵流风
 * @ClassName TestApplication
 * @Date 2021/12/24 17:17
 * @packageName cn.zq.spshot.test
 * @Description TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class);
    }

    @Bean
    public WeiXinUtil weiXinUtil(){
        return new WeiXinUtil();
    }

}
