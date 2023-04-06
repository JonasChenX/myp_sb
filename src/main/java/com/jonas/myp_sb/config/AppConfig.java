package com.jonas.myp_sb.config;

import com.jonas.myp_sb.example.ioDemo.Resources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class AppConfig {
    @Bean
    public Resources getResources(ResourceLoader resourceLoader) {
        return new Resources(resourceLoader);
    }
}
