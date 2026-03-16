package com.easechat.easechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan("com.easechat.easechat.mapper")
@ConfigurationPropertiesScan
public class EasechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasechatApplication.class, args);
	}

}
