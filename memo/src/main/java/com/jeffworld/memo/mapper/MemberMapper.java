package com.jeffworld.memo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.jeffworld.memo.dto.Member;

@Mapper
public interface MemberMapper {
	Member findUserByEmail(String email);
	int isDuplicated(String email);
	int registerMember(Member member);
	int registerAuthorization(String email);
}