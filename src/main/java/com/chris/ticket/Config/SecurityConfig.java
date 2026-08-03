package com.chris.ticket.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.chris.ticket.Entities.Role;
import com.chris.ticket.Filters.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
	
	private final UserDetailsService userDetailsService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		var provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
		.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.csrf(c -> c.disable())
		.authorizeHttpRequests(c -> c
				.anyRequest().permitAll())
		;
	
	return http.build();
	}
	*/
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
		.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.csrf(c -> c.disable())
		.authorizeHttpRequests(c -> c
				.requestMatchers("/start/**").permitAll()
				.requestMatchers("/favicon.ico","/favicon**", "/favicon.ico/**").permitAll()
				.requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
				.requestMatchers("/tickets/customer/**").hasRole(Role.CUSTOMER.name())
				.requestMatchers("/tickets/support/**").hasRole(Role.SUPPORT.name())
				.requestMatchers("/tickets/*").hasAnyRole(Role.CUSTOMER.name(),Role.SUPPORT.name())
				.requestMatchers("/reply/**").hasAnyRole(Role.CUSTOMER.name(),Role.SUPPORT.name())
				.requestMatchers(HttpMethod.POST,"/users").permitAll()
				.requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
				.requestMatchers(HttpMethod.POST,"/auth/refresh").permitAll()
				.requestMatchers(HttpMethod.POST,"/auth/logout").permitAll()
				.requestMatchers("/js/**").permitAll()
				.requestMatchers("/html/**").permitAll()
				.requestMatchers("/css/**").permitAll()
				.anyRequest().authenticated())
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
		.exceptionHandling(c -> c
			    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
			    .accessDeniedHandler((req, res, ex) ->
			        res.setStatus(HttpStatus.FORBIDDEN.value()))
			);
	
	return http.build();
	}
	
	
	/*
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
			.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.csrf(c -> c.disable())
			.authorizeHttpRequests(c -> c
					.requestMatchers("/start/**").permitAll()
					.requestMatchers("/favicon.ico","/favicon**", "/favicon.ico/**").permitAll()
					.requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
					.requestMatchers("/tickets/customer/**").hasRole(Role.CUSTOMER.name())
					.requestMatchers(HttpMethod.POST,"/users").permitAll()
					.requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
					.requestMatchers(HttpMethod.POST,"/auth/refresh").permitAll()
					.requestMatchers("/js/**").permitAll()
					.requestMatchers("/html/**").permitAll()
					.anyRequest().authenticated())
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling(c -> {c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
									c.accessDeniedHandler((request, response, accessDeniedException) -> 
									response.setStatus(HttpStatus.FORBIDDEN.value()));
									});
		
		return http.build();
	}
	*/
}
