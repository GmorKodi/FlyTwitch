package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.services.TwitchService;
import com.example.springboot.HelloController;

@Configuration
public class ServiceConfig {
	
	@Bean
	public TwitchService twitchService() {
		return new TwitchService();
	}
	

}
