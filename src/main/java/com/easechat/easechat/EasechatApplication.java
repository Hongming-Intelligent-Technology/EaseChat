package com.easechat.easechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.easechat.easechat.mapper")
@SpringBootApplication
public class EasechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasechatApplication.class, args);
	}

}
