package com.web.banbut.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // hoặc "http://localhost:3000" nếu chỉ cho phép FE local
                .allowedMethods("*") // GET, POST, PUT, DELETE, OPTIONS
                .allowedHeaders("*") // Content-Type, Authorization, ...
                .allowCredentials(false); // true nếu cần gửi cookie
    }
}
