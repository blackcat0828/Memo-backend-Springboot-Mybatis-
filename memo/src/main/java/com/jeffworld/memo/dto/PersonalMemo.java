package com.jeffworld.memo.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PersonalMemo {
	private int id;
	private String title;
	private String contents;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date;
	private int boardId;
}
