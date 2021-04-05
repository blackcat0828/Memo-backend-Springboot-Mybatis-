package com.jeffworld.memo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.jeffworld.memo.common.utils.AuthUtil;
import com.jeffworld.memo.dto.Member;
import com.jeffworld.memo.service.MemberServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberRestController {
	@Autowired
	MemberServiceImpl memberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//회원 가입시 email 중복확인
	@PostMapping("/auth/signup/duplicate")
	public ResponseEntity<String> isDuplicated(@RequestBody Member member){
		String email = member.getEmail();
		if(memberService.isDuplicated(email) != 1) {
			return new ResponseEntity<>("duplicated", HttpStatus.CONFLICT);
		} 
		
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
	
	//회원가입
	@PostMapping("/auth/signup")
	public ResponseEntity<String> signup(@RequestBody Member member) throws Exception{
		String EncryptPassword = passwordEncoder.encode(member.getPassword());
		log.info("페스워드 인코딩 값 : " + EncryptPassword);
		member.setPassword(EncryptPassword);
		int isRegistered = memberService.registerMember(member);
		String email = member.getEmail();
		if(isRegistered == 1) {
			memberService.registerAuthorization(email);
		}
		
		
		//return 401
		return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
	}
	
	
	//인증후 로그인 유저 정보 요청
	@GetMapping("/users/me")
	public ResponseEntity<Object> getUserInfo(@RequestHeader(name="Authorization") String header) throws Exception{
		log.info("JWT 접근 테스트");
		String email = AuthUtil.getUserEmail(header);
		Member member = memberService.findUserByEmail(email);
		
		return new ResponseEntity<>(member, HttpStatus.OK);
	}
	
	
}
