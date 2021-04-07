package com.jeffworld.memo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeffworld.memo.dto.Member;
import com.jeffworld.memo.mapper.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl {
	
	@Autowired
	private MemberMapper memberMapper;
	
	//로그인 후 유저 정보 가져오기
	public Member findUserByEmail(String email) {
		Member member = memberMapper.findUserByEmail(email);
		return member;
	}
	
	//중복확인
	public int isDuplicated (String email) {
		int isDuplicated = memberMapper.isDuplicated(email);
		log.info("중복 쿼리 리턴 값 확인 " + isDuplicated);
		return isDuplicated;
	}
	
	
	//회원가입
	public int registerMember(Member member) throws Exception {
		int isRegistered = memberMapper.registerMember(member);
		
		return isRegistered;
	
	}
	
	//회원 권한 생성 기본 MEMBER
	public int registerAuthorization(String email) throws Exception{
		int isRegistered = memberMapper.registerAuthorization(email);
		return isRegistered;
	}

	
	
}
