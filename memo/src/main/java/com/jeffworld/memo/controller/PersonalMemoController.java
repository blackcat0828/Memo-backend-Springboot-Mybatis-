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

import com.jeffworld.memo.dto.BoardMember;
import com.jeffworld.memo.dto.Criteria;
import com.jeffworld.memo.dto.PersonalBoard;
import com.jeffworld.memo.dto.PersonalMemo;
import com.jeffworld.memo.service.PersonalMemoServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PersonalMemoController {
	@Autowired
	PersonalMemoServiceImpl personalMemoService;

	//내가 주인인 보드 목록 가져오기
	@GetMapping("/boards/personal")
	public ResponseEntity<Object> getBoardsList(@RequestParam String email) throws Exception{
		List<PersonalBoard> personalBoard = personalMemoService.findPersonalBoardByEmail(email);
		
		return new ResponseEntity<>(personalBoard, HttpStatus.OK);
	}
	
	//내가 맴버인 보드 목록 가져오기
	@GetMapping("/boards/member")
	public ResponseEntity<Object> getTeamBoardsList(@RequestParam String email) throws Exception{
		List<PersonalBoard> teamBoard = personalMemoService.findTeamBoardByEmail(email);
		
		return new ResponseEntity<>(teamBoard, HttpStatus.OK);
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
	
	//개인 메모 총 갯수 가져오기
	@GetMapping("/boards/personal/{pboardid}/length")
	public ResponseEntity<Object> getPersonalMemosLength(
			@PathVariable("pboardid") int pboardid) throws Exception{
			
		int memoLength = personalMemoService.getPersonalMemosLength(pboardid);
		
		return new ResponseEntity<>(memoLength, HttpStatus.OK);
	}
	
	//개인 메모 리스트 가져오기
	@GetMapping("/boards/personal/{pboardid}")
	public ResponseEntity<Object> getPersonalMemos(
			@PathVariable("pboardid") int pboardid,
			@RequestParam("perPage") int perPage, 
            @RequestParam("currentPage") int currentPage) throws Exception{			
			int curPage = (currentPage - 1) * 4;
			Criteria criteria = new Criteria();
			criteria.setCurrentPage(curPage);
			criteria.setPboardid(pboardid);
			criteria.setPerPage(perPage);
		List<PersonalMemo> personalMemos = personalMemoService.getPersonalMemos(criteria);
		
		return new ResponseEntity<>(personalMemos, HttpStatus.OK);
	}
	
	//제목으로 검색된 개인 메모 총 갯수 가져오기
	@GetMapping("/boards/personal/{pboardid}/search/length")
	public ResponseEntity<Object> getPersonalMemosLengthWithTitle(
			@PathVariable("pboardid") int pboardid,
			@RequestParam("title") String Title
			) throws Exception{
			Criteria criteria = new Criteria();
			criteria.setPboardid(pboardid);
			criteria.setTitle(Title);
			
		int memoLength = personalMemoService.getPersonalMemosLengthWithTitle(criteria);
		
		return new ResponseEntity<>(memoLength, HttpStatus.OK);
	}
	
	//제목으로 검색된 개인 메모 리스트 가져오기
	@GetMapping("/boards/personal/{pboardid}/search")
	public ResponseEntity<Object> getPersonalMemosWithTitle(
			@PathVariable("pboardid") int pboardid,
			@RequestParam("perPage") int perPage, 
            @RequestParam("currentPage") int currentPage,
            @RequestParam("title") String Title) throws Exception{			
			int curPage = (currentPage - 1) * 4;
			Criteria criteria = new Criteria();
			criteria.setCurrentPage(curPage);
			criteria.setPboardid(pboardid);
			criteria.setPerPage(perPage);
			criteria.setTitle(Title);
		List<PersonalMemo> personalMemos = personalMemoService.getPersonalMemosWithTitle(criteria);
		
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
	
	//메모 삭제
	@DeleteMapping("/boards/personal/{pboardid}/memos/{memoId}")
	public ResponseEntity<String> deletePersonalMemo(@PathVariable("memoId") int memoId){
		personalMemoService.deletePersonalMemo(memoId);
		return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
	}
	
	//메모장 멤버 추가
	@PostMapping("/boards/personal/{pboardid}/member")
	public ResponseEntity<String> addPersonalMemo(@PathVariable("pboardid") int pboardid, @RequestBody BoardMember boardMember){
		boardMember.setPboardid(pboardid);
		personalMemoService.addBoardMember(boardMember);
		return new ResponseEntity<>("등록 완료", HttpStatus.CREATED);
	}

	
	//메모장 맴버 삭제
	@DeleteMapping("/boards/personal/{pboardid}/member")
	public ResponseEntity<String> deleteBoardMember(@PathVariable("pboardid") int pboardid, @RequestParam String boardMember){
		BoardMember member = new BoardMember();
		member.setPboardid(pboardid);
		member.setBoardMember(boardMember);
		personalMemoService.deleteBoardMember(member);
		return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
	}
}
