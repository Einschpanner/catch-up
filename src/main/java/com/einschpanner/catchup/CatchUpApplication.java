package com.einschpanner.catchup;

import com.einschpanner.catchup.global.configs.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class CatchUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatchUpApplication.class, args);
    }

}
