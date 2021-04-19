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
		log.info("security config ...");
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
		
//		http.authorizeRequests()
//		.antMatchers("/board/list")
//		.permitAll();
//		
//		http.authorizeRequests()
//		.antMatchers("/board/register")
//		.hasAuthority("MEMBER");
//		
//		http.authorizeRequests()
//		.antMatchers("/notice/list")
//		.permitAll();
//		
		http.authorizeRequests()
		.antMatchers("/auth/signin").permitAll()
		.antMatchers("/auth/signup/**").permitAll()
		.antMatchers("/**").hasAnyAuthority("ADMIN","MEMBER")
		.antMatchers("/admin/**").hasAuthority("ADMIN");
		
//		
//		
//		http.exceptionHandling()
//		.accessDeniedHandler(createAccessDeniedHandler());
//		
//		http.logout()
//		.logoutUrl("/logout")
//		.invalidateHttpSession(true);
		
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(createUserDetailsService())
		.passwordEncoder(createPasswordEncoder());
		
		
		
//		auth.inMemoryAuthentication()
//		.withUser("member")
//		.password("{noop}1234")
//		.roles("MEMBER");
//		
//		auth.inMemoryAuthentication()
//		.withUser("admin")
//		.password("{noop}1234")
//		.roles("ADMIN");
		//사용자 정의테이블로 인증 처리하기 위한 SQL 쿼리
//		String queryForMember = "SELECT email, password, enabled FROM member where email = ?";
//		String queryForRole = "select a.email, a.authority from auth a, member b where a.email = b.email and b.email = ?";
//		
//		// UserDetailsService 재정의
//		// 스프링 시큐리티의 UserDetailsService를 구현하여 사용자 상세 정보를 얻어오는 메서드를 재정의한다.
//		//CustomUserDetailService 빈을 인증 제공자에게 지정한다.
//		auth.userDetailsService(createUserDetailsService())
//		.passwordEncoder(createPasswordEncoder());
//		
//		auth.jdbcAuthentication()
//		//데이터 소스를 지정
//		.dataSource(dataSource)
//		//작성한 쿼리를 지정
//		.usersByUsernameQuery(queryForMember)
//		.authoritiesByUsernameQuery(queryForRole)
//		//실제 가입시엔 스프링 시큐리티에서 제공되는 BCryptPasswordEncoder 클래스를 빈으로 등록해서 여기 주입해야한다.
//		//기본적으로 스프링5부터는 페스워드가 Encrypt 되어 들어가기 때문
//		.passwordEncoder(createPasswordEncoder());
	}	
	

	
	@Bean
	public PasswordEncoder createPasswordEncoder() {
		//실제 암호화를 위해 들어가야할 값 return new BCryptPasswordEncoder();
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
		source.registerCorsConfiguration("/**", configuration);
		
		
		return source;
	}
	
}
