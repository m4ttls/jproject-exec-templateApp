package com.curcico.jproject.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	  auth.inMemoryAuthentication().withUser("user").password("123456").roles("ADMIN", 
			  "GET_FOO", "PUT_FOO", "POST_FOO", "DELETE_FOO",
			  "GET_BAR", "PUT_BAR", "POST_BAR", "DELETE_BAR");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http
	  .csrf().disable() // WARN, enable csrf in production enviroment
	  .authorizeRequests()
		.antMatchers("/secure/**").access("hasRole('ROLE_ADMIN')")
		.and().formLogin();

      http.authorizeRequests().antMatchers("/").permitAll().and()
      .authorizeRequests().antMatchers("/console/**").permitAll();
      http.csrf().disable();
      http.headers().frameOptions().disable();
	}
}
