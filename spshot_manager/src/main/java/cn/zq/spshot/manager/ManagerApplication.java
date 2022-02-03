package cn.zq.spshot.manager;

import cn.zq.spshot.common.utils.JwtUtil;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author 华韵流风
 * @ClassName ManagerApplication
 * @Date 2021/10/20 16:54
 * @packageName com.tensquare.manager
 * @Description TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                // 如果要强制使用https，请松开以下注释
//                SecurityConstraint constraint = new SecurityConstraint();
//                constraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                constraint.addCollection(collection);
//                context.addConstraint(constraint);
//            }
//        };
//        // 添加http
//        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
//        return tomcat;
//    }
//
//    // 配置http
//    private Connector createStandardConnector() {
//        // 默认协议为org.apache.coyote.http11.Http11NioProtocol
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setSecure(true);
//        connector.setScheme("http");
//        connector.setPort(port);
//        // 当http重定向到https时的https端口号
//        connector.setRedirectPort(httpsPort);
//        return connector;
//    }
//
//    @Value("${http.port}")
//    private Integer port;
//
//    @Value("${server.port}")
//    private Integer httpsPort;
}
