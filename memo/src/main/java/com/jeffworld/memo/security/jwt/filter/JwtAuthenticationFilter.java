package com.jeffworld.memo.security.jwt.filter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jeffworld.memo.security.domain.CustomUser;
import com.jeffworld.memo.security.jwt.SecurityConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		//지정된 URL로 요청이 들어오면 해당 JWT 발급용 Authentication Filter동작
		setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		return authenticationManager.authenticate(authenticationToken);
	}
	
	//인증에 성공후 JWT을 생성해 Client에게 response한다.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain, Authentication authentication) throws IOException {
		
		CustomUser user = ((CustomUser)authentication.getPrincipal());
		List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		
		
		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
		String accessToken = Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
				.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
				.setIssuer(SecurityConstants.TOKEN_ISSUER)
				.setAudience(SecurityConstants.TOKEN_AUDIENCE)
				.setSubject("" + user.getMember().getEmail())
				.setExpiration(new Date(System.currentTimeMillis()+864000000))
				.claim("rol", roles)
				.compact();
		
		
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(
	            "{\"" + SecurityConstants.TOKEN_HEADER + "\":\"" + accessToken + "\"}"
	    );
	
	}

}
