package com.watsonas.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	Logger logger = LoggerFactory.getLogger(WebSecurityConfigurerAdapter.class);

	@Value("${publicName}")
	private String userName;
	
	@Value("${publicPassword}")
	private String userPassword;
	
	@Value("${security.user.name}")
	private String adminName;
	
	@Value("${security.user.password}")
	private String adminPassword;
	
	// Authentication : User --> Roles
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		logger.info( userName + " has PUBLIC role");
		auth.inMemoryAuthentication().passwordEncoder(
				org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance())
				.withUser(userName).password(userPassword).roles("PUBLIC").and()
				.withUser(adminName).password(adminPassword).roles("PUBLIC", "ADMIN");
	}

	// restrict access to control heating system to admin users only
	// from Chrome, works as expected. From other browsers, the control isn't enabled :-(
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
		.and().authorizeRequests().antMatchers("/setHeatingSystemControl/**").hasRole("ADMIN")
		.and().authorizeRequests().antMatchers("/*").hasAnyRole("PUBLIC","ADMIN")
		.and().csrf().disable().headers().frameOptions().disable();
	}
}