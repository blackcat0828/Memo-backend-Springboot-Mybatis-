package com.jeffworld.memo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jeffworld.memo.dto.Member;
import com.jeffworld.memo.security.domain.CustomUser;
import com.jeffworld.memo.service.MemberServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private MemberServiceImpl memberService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("Load User By UserName: " + email);
		
		//userName은 사용자명이 아니라 사용자 아이디를 의미한다.
		Member member = memberService.findUserByEmail(email);

		return member == null ? null : new CustomUser(member);
	}
	
}
