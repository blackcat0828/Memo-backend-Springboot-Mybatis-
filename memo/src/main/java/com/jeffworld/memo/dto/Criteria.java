package com.jeffworld.memo.dto;

import lombok.Data;

@Data
public class Criteria {
	private int pboardid;
	private int currentPage;
	private int perPage;
	//검색조건: 제목
	private String title;
}
