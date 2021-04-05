package com.jeffworld.memo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.jeffworld.memo.dto.Member;

@Mapper
public interface MemberMapper {
	Member findUserByEmail(String email);
	void registerMember(Member member);
	void registerAuthorization(String email);
}