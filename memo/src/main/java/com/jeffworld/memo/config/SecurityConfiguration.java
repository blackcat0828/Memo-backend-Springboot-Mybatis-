package com.jeffworld.memo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jeffworld.memo.security.CustomUserDetailsService;
import com.jeffworld.memo.security.jwt.filter.JwtAuthenticationFilter;
import com.jeffworld.memo.security.jwt.filter.JwtAuthorizationFilter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors()
		.and()
		.csrf()
		.disable()
		// JWT 인증 필터 보안 컨텍스트에 추가
		.addFilter(new JwtAuthenticationFilter(authenticationManager()))
		// JWT 인가 필터 보안 컨텍스트에 추가
		.addFilter(new JwtAuthorizationFilter(authenticationManager()))
		//세션 관리 비활성화
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		

		http.authorizeRequests()
		.antMatchers("/auth/signin").permitAll()
		.antMatchers("/auth/signup/**").permitAll()
		.antMatchers("/**").hasAnyAuthority("ADMIN","MEMBER")
		.antMatchers("/admin/**").hasAuthority("ADMIN");
		
		
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(createUserDetailsService())
		.passwordEncoder(createPasswordEncoder());
		

	}	
	

	
	@Bean
	public PasswordEncoder createPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//스프링 시큐리티의 UserDetailsService를 구현한 클래스를 빈으로 등록한다.
	@Bean
	public UserDetailsService createUserDetailsService() {
		return new CustomUserDetailsService();
	}
	
	//CORS 설정
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
	    configuration.addAllowedMethod("*");
	    configuration.addAllowedHeader("*");
	    configuration.setMaxAge(3600L);
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		//configuration.applyPermitDefaultValues()
		//PUT, DELETE를 지원안함
		source.registerCorsConfiguration("/**", configuration);
		
		
		return source;
	}
	
}
