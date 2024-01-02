package com.jonas.myp_sb.config;

import com.jonas.myp_sb.example.ods.DownloadableResourceMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompostWebConfig {
    @Bean
    public DownloadableResourceMessageConverter downloadableResourceMessageConverter() {
        return new DownloadableResourceMessageConverter();
    }
}
