package com.cos.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.security.config.oauth.PrincipalOauth2UserService;


@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	//해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/user/**").authenticated()
		.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.anyRequest().permitAll()
		.and()
		.formLogin()
		.loginPage("/loginForm")
		.loginProcessingUrl("/login")
		.defaultSuccessUrl("/")
		.and()
		.oauth2Login()
		.loginPage("/loginForm")
		.userInfoEndpoint()
		.userService(principalOauth2UserService);
	}
}
