package com.radek.myownplengdictionary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import com.radek.myownplengdictionary.service.UserService;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomAuthenticationSuccessHandler theCustomAuthenticationSuccessHandler;
	
	//user authentication based on db data
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	
	//encoding filters must be placed before security filters
	//only admins have authority to manage user's list in the /users section
	//only registered users have authority to use app
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new EncodingFilter(), ChannelProcessingFilter.class);
		
		http.authorizeRequests()		
			.antMatchers("/").hasAnyAuthority("ADMIN", "USER")
			.antMatchers("/users/**").hasRole("ADMIN")
			.antMatchers("/resources/**").permitAll()
			.and()
			.formLogin()
				.successHandler(theCustomAuthenticationSuccessHandler)
				.loginPage("/loginPage")
				.loginProcessingUrl("/authenthicateTheUser")
				.permitAll()
			.and().logout().permitAll()
			.and().exceptionHandling().accessDeniedPage("/accessDenied");
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userService);
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return daoAuthenticationProvider;
	}
}
