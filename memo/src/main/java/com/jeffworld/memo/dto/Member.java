package com.jeffworld.memo.dto;

import java.util.List;

import lombok.Data;

@Data
public class Member {
	private String email;
	private String name;
	private String password;
	private boolean enabled;
	private List<MemberAuth> authList;
}