package com.jeffworld.memo.dto;

import java.util.List;

import lombok.Data;

@Data
public class PersonalBoard {
	private int pboardid;
	private String title;
	private String owner;
	private List<BoardMember> memberList;
}
