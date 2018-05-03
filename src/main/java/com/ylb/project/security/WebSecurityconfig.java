package com.ylb.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityconfig extends WebSecurityConfigurerAdapter{
	@Autowired
	MyUserDetailsService myUserDetailsService;
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
	}
	protected void configure(HttpSecurity http) throws Exception{
		//允许所有用户访
		http.authorizeRequests()
			.antMatchers("/AmazeUI-2.4.2/**","/basic/**","/css/**","/images/**","/js/**").permitAll()
			.antMatchers("/search","/home","home/find","/home/introduction","/register","/user/do_register","/user/verifyUser","/user/do_login").permitAll()
			.anyRequest().authenticated()//其他的都需要授权
		.and()
		.formLogin()
			.loginPage("/login")//指定登录页面
			.loginProcessingUrl("/user/do_login")
			.defaultSuccessUrl("/home")
			.usernameParameter("userName")
			.passwordParameter("password")
			.and()
		.logout()
			.permitAll()
			.logoutSuccessUrl("/login");
		}
}
