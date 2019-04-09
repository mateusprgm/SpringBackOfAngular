//package com.algaworks.comercial.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//
//
//@Configuration
//public class InMemorySecurityConfig {
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder builder)
//		throws Exception {
//		builder 
//			.inMemoryAuthentication()
//			.withUser("mateus").password("{noop}123").roles("USER")
//			.and()
//			.withUser("joao").password("{noop}1234").roles("USER")
//			.and()
//			.withUser("luis").password("{noop}12").roles("USER");
//	}
//}