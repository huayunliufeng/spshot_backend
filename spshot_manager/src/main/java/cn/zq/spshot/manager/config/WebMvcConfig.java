package cn.zq.spshot.manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 华韵流风
 * @ClassName WebMvcConfig
 * @Date 2021/10/25 15:39
 * @packageName com.tensquare.web.config
 * @Description TODO
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE").maxAge(36000).allowCredentials(true);
    }
}
