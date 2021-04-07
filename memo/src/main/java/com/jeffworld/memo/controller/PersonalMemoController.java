package com.jeffworld.memo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jeffworld.memo.dto.PersonalBoard;
import com.jeffworld.memo.service.PersonalMemoServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PersonalMemoController {
	@Autowired
	PersonalMemoServiceImpl personalMemoService;
	
	@GetMapping("/boards")
	public ResponseEntity<Object> getBoardsList(@RequestBody PersonalBoard pBoard) throws Exception{
		String owner = pBoard.getOwner();
		log.info("owner 값 : " + owner);
		List<PersonalBoard> personalBoard = personalMemoService.findPersonalBoardByEmail(owner);
		
		return new ResponseEntity<>(personalBoard, HttpStatus.OK);
	}
}
