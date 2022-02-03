package cn.zq.spshot.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 华韵流风
 * @ClassName WebSecurityConfig
 * @Date 2021/10/9 16:07
 * @packageName com.tensquare.user.config
 * @Description TODO
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * authorizeRequests：所有security全注解配置实现的开端，表示开始说明需要的权限
         * 需要的权限分为两部分，第一部分是拦截的路径，第二部分是访问该路径需要的权限
         * antMatchers  拦截的路径
         * permitAll()所有权限都可以，直接放行所有
         * hasAnyRole()这个方法可以指定角色	anyRequest任何请求
         * authenticated需要认证后才能访问
         * .and().csrf().disable(); 固定写法
         * 使csrf攻击失效，
         * 如果不设置为disabled，所有除了内部的请求，其余外部请求都被认为是在攻击我这个网站，全部拦截
         */
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
