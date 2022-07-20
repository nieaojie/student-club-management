package priv.naj.club;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@MapperScan(value = "priv.naj.club.*.mapper")
public class AppServer {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = SpringApplication.run(AppServer.class, args);
        Environment environment = context.getBean(Environment.class);
        String contextPath = environment.getProperty("server.servlet.context-path");
        if (contextPath == null || "".equals(contextPath)) {
            contextPath = "";
        }
        log.info("\n访问地址：http://{}:{}{}", InetAddress.getLocalHost().getHostAddress(),
                 environment.getProperty("server.port"), contextPath);
        log.info("\nSwagger地址：http://{}:{}{}", InetAddress.getLocalHost().getHostAddress(),
                 environment.getProperty("server.port"), "/swagger-ui.html");
    }
}


