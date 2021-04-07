package com.jeffworld.memo.dto;

import lombok.Data;

@Data
public class PersonalMemo {
	private int id;
	private String title;
	private String contents;
	private int boardId;
}
