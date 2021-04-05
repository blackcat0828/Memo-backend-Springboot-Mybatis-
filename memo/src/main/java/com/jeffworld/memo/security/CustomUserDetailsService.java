package com.jeffworld.memo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jeffworld.memo.dto.Member;
import com.jeffworld.memo.mapper.MemberMapper;
import com.jeffworld.memo.security.domain.CustomUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("Load User By UserName: " + email);
		
		//userName은 사용자명이 아니라 사용자 아이디를 의미한다.
		Member member = memberMapper.findUserByEmail(email);
		log.info("쿼리가 넘어오나? 성공?");
		log.info("queried by member mapper: " + member);

		return member == null ? null : new CustomUser(member);
	}
	
}
