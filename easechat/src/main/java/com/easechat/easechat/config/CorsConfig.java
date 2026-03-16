package com.easechat.easechat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private final CorsProperties corsProperties;

    public CorsConfig(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        String[] allowedOrigins = corsProperties.getAllowedOrigins();
        if (allowedOrigins != null && allowedOrigins.length > 0) {
            registry.addMapping("/**") // 允许跨域访问的路径
                    .allowedOrigins(allowedOrigins)
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法
                    .allowCredentials(true) // 是否发送 Cookie
                    .maxAge(3600) // 预检间隔时间
                    .allowedHeaders("*"); // 允许的头部设置
        }
    }
}