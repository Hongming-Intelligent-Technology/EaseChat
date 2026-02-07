package com.easechat.easechat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许跨域访问的路径
                .allowedOriginPatterns("*") // 允许所有域名 (Spring Boot 2.4+ 使用 allowedOriginPatterns)
                // .allowedOrigins("http://localhost:5173") // 为了安全，生产环境建议指定具体域名
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法
                .allowCredentials(true) // 是否发送 Cookie
                .maxAge(3600) // 预检间隔时间
                .allowedHeaders("*"); // 允许的头部设置
    }
}