package com.jeffworld.memo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.jeffworld.memo.common.utils.AuthUtil;
import com.jeffworld.memo.dto.Member;
import com.jeffworld.memo.mapper.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberRestConstroller {
	@Autowired
	MemberMapper memberMapper;
	@PostMapping("/auth/signin/duplicate")
	public ResponseEntity<String> signin(@RequestBody String email){
		
		if(memberMapper.findUserByEmail(email) != null) {
			return new ResponseEntity<>("duplicated", HttpStatus.CONFLICT);
		} 
		
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
	//회원가입
	@PostMapping("/auth/signin")
	public ResponseEntity<String> signin(@RequestBody Member member){
		
	}
	
	//인증후 로그인 유저 정보 요청
	@GetMapping("/users/me")
	public ResponseEntity<Object> getUserInfo(@RequestHeader(name="Authorization") String header) throws Exception{
		log.info("JWT 접근 테스트");
		String email = AuthUtil.getUserEmail(header);
		Member member = memberMapper.findUserByEmail(email);
		
		return new ResponseEntity<>(member, HttpStatus.OK);
	}
	
	
}
