package cn.pridezh.rbac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * @author PrideZH
 * @since 2022/8/3 18:44
 */
@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(Application.class, args);

        Environment env = application.getEnvironment();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");

        String ip = Inet4Address.getLocalHost().getHostAddress();
        log.info("Swagger文档: http://{}:{}{}/doc.html", ip, port, path);
    }

}
