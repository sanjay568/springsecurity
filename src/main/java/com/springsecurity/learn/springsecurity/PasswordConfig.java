package com.springsecurity.learn.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springsecurity.learn.springsecurity.jwt.JwtConfig;

@Configuration
public class PasswordConfig {
	
	@Bean
	public PasswordEncoder getPasswordEncode() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	public JwtConfig jwtConfig() {
		return new JwtConfig();
	}
}
