package com.jeffworld.memo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeffworld.memo.dto.PersonalBoard;
import com.jeffworld.memo.mapper.PersonalMemoMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PersonalMemoServiceImpl {
	@Autowired
	private PersonalMemoMapper personalMemoMapper;
	
	public List<PersonalBoard> findPersonalBoardByEmail(String email){
		log.info("서비스에 오는 email 값 : " + email);
		List<PersonalBoard> boards = personalMemoMapper.findPersonalBoardByEmail(email);
		return boards;
	}
}
