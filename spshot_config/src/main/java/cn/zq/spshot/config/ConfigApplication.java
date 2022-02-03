package cn.zq.spshot.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author 华韵流风
 * @ClassName ConfigApplication
 * @Date 2021/11/27 20:51
 * @packageName cn.zq.spshot.config
 * @Description TODO
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class);
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
//        //设置为false后，所有的http请求会被强制转换为https请求
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
