package com.jeffworld.memo.common.utils;

import com.jeffworld.memo.security.jwt.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

//JWT 헤더값을 해석하여 사용자 정보 획득
@Slf4j
public class AuthUtil {
	public static String getUserEmail(String header) throws Exception{
		String token = header.substring(7);
		log.info("섭스트링 토큰 값 : " + token);
		
		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
		
		Jws<Claims> parsedToken = Jwts.parser()
									.setSigningKey(signingKey)
									.parseClaimsJws(token);
		
		String email = parsedToken.getBody().getSubject();
		return email;
	
	
	}
}
