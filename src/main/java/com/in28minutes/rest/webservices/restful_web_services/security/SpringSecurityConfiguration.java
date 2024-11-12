package com.in28minutes.rest.webservices.restful_web_services.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		1) 모든 요청이 인증돼야 한다.
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated()
				);
		
//		2) 요청이 인증되지 않았다면, 기본 값으로 웹 페이지가 나타남.
		http.httpBasic(withDefaults());
		
//		3) CSRF 체크 설정.
		http.csrf().disable();
		
		
		
		return http.build();
	}
	
}
