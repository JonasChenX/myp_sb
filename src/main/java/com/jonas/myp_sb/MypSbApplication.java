package com.jonas.myp_sb;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Optional;

@SpringBootApplication
public class MypSbApplication {

    private static final Logger log = LoggerFactory.getLogger(MypSbApplication.class);


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MypSbApplication.class);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    /**
     * log顯示 ApplicationName Local Profile
     */
    private static void logApplicationStartup(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        String serverPort = env.getProperty("server.port");
        String contextPath = Optional
                .ofNullable(env.getProperty("server.servlet.context-path"))
                .filter(StringUtils::isNotBlank)
                .orElse("/");
        log.info(
                "\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running!\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "swagger: \t{}://localhost:{}{}/swagger-ui.html\n\t" +
                        "Profile(s): {}" +
                "\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                //Local
                protocol,
                serverPort,
                contextPath,
                //swagger
                protocol,
                serverPort,
                contextPath,
                //Profile(s)
                env.getActiveProfiles()
        );
    }

}
