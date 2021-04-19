package com.jeffworld.memo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeffworld.memo.dto.PersonalBoard;
import com.jeffworld.memo.dto.PersonalMemo;
import com.jeffworld.memo.service.PersonalMemoServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PersonalMemoController {
	@Autowired
	PersonalMemoServiceImpl personalMemoService;
	
	@GetMapping("/boards/personal")
	public ResponseEntity<Object> getBoardsList(@RequestParam String email) throws Exception{
		log.info("PersonalMemoController boards리스트 get 실행");
		List<PersonalBoard> personalBoard = personalMemoService.findPersonalBoardByEmail(email);
		
		return new ResponseEntity<>(personalBoard, HttpStatus.OK);
	}
	
	@PostMapping("/boards/personal")
	public ResponseEntity<Object> addPersonalBoards(@RequestBody PersonalBoard board){
		personalMemoService.addPersonalBoard(board);
		PersonalBoard returnboard = board;
		return new ResponseEntity<>(returnboard, HttpStatus.CREATED);
	}
	
	@PutMapping("/boards/personal/{pboardid}")
	public ResponseEntity<String> updatePersonalBoards(@RequestBody PersonalBoard board, @PathVariable("pboardid") int pboardid){
	board.setPboardid(pboardid);
	personalMemoService.updatePersonalBoard(board);
	return new ResponseEntity<>("수정 완료", HttpStatus.OK);
	}
	
	@DeleteMapping("/boards/personal/{pboardid}")
	public ResponseEntity<String> DeletePersonalBoards(@PathVariable("pboardid") int pboardid){
		personalMemoService.deletePersonalBoard(pboardid);
		return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
	}
	
	//개인 메모 리스트 가져오기
	@GetMapping("/boards/personal/{pboardid}")
	public ResponseEntity<Object> getPersonalMemos(@PathVariable("pboardid") int pboardid) throws Exception{
		List<PersonalMemo> personalMemos = personalMemoService.getPersonalMemos(pboardid);
		
		return new ResponseEntity<>(personalMemos, HttpStatus.OK);
	}
	
	//개인 메모 추가
	@PostMapping("/boards/personal/{pboardid}/memos")
	public ResponseEntity<String> addPersonalMemo(@RequestBody PersonalMemo Memo, @PathVariable("pboardid") int pboardid){
		Memo.setBoardId(pboardid);
		personalMemoService.addPersonalMemo(Memo);
		return new ResponseEntity<>("등록 완료", HttpStatus.CREATED);
	}
	
	//특정 개인 메모 가져오기
	@GetMapping("/boards/personal/{pboardid}/memos/{memoId}")
	public ResponseEntity<Object> getPersonalMemo(@PathVariable("memoId") int memoId){
		PersonalMemo personalMemo = personalMemoService.getPersonalMemo(memoId);
		
		return new ResponseEntity<>(personalMemo, HttpStatus.OK);
	}
	
	//개인 메모 추가
	@PutMapping("/boards/personal/{pboardid}/memos/{memoId}")
	public ResponseEntity<String> updatePersonalMemo(@RequestBody PersonalMemo Memo, @PathVariable("memoId") int memoId){
		Memo.setId(memoId);
		personalMemoService.updatePersonalMemo(Memo);
		return new ResponseEntity<>("수정 완료", HttpStatus.OK);
	}
	
	@DeleteMapping("/boards/personal/{pboardid}/memos/{memoId}")
	public ResponseEntity<String> DeletePersonalMemo(@PathVariable("memoId") int memoId){
		personalMemoService.deletePersonalMemo(memoId);
		return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
	}
}
