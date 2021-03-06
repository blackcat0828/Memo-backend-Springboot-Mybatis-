package com.jeffworld.memo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeffworld.memo.dto.BoardMember;
import com.jeffworld.memo.dto.Criteria;
import com.jeffworld.memo.dto.PersonalBoard;
import com.jeffworld.memo.dto.PersonalMemo;
import com.jeffworld.memo.mapper.PersonalMemoMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PersonalMemoServiceImpl {
	@Autowired
	private PersonalMemoMapper personalMemoMapper;
	
	public List<PersonalBoard> findPersonalBoardByEmail(String email){
		List<PersonalBoard> boards = personalMemoMapper.findPersonalBoardByEmail(email);
		return boards;
	}
	
	public List<PersonalBoard> findTeamBoardByEmail(String email){
		List<PersonalBoard> boards = personalMemoMapper.findTeamBoardByEmail(email);
		return boards;
	}
	
	public void addPersonalBoard(PersonalBoard board) {
		personalMemoMapper.addPersonalBoard(board);
	}
	
	public void updatePersonalBoard(PersonalBoard board) {
		personalMemoMapper.updatePersonalBoard(board);
	}
	
	public void deletePersonalBoard(int pboardid) {
		personalMemoMapper.deletePersonalBoard(pboardid);
	}
	
	public int getPersonalMemosLength(int pboardid){
		int memosLength = personalMemoMapper.getPersonalMemosLength(pboardid);
		return memosLength;
	}
	
	public List<PersonalMemo> getPersonalMemos(Criteria criteria){
		List<PersonalMemo> memos = personalMemoMapper.getPersonalMemos(criteria);
		return memos;
	}
	
	public int getPersonalMemosLengthWithTitle(Criteria criteria){
		int memosLength = personalMemoMapper.getPersonalMemosLengthWithTitle(criteria);
		return memosLength;
	}
	
	public List<PersonalMemo> getPersonalMemosWithTitle(Criteria criteria){
		List<PersonalMemo> memos = personalMemoMapper.getPersonalMemosWithTitle(criteria);
		return memos;
	}
	
	public void addPersonalMemo(PersonalMemo Memo) {
		personalMemoMapper.addPersonalMemo(Memo);
	}
	
	public PersonalMemo getPersonalMemo(int memoId) {
		PersonalMemo memo = personalMemoMapper.getPersonalMemo(memoId);
		return memo;
	}
	
	public void updatePersonalMemo(PersonalMemo Memo) {
		personalMemoMapper.updatePersonalMemo(Memo);
	}
	
	public void deletePersonalMemo(int memoId) {
		personalMemoMapper.deletePersonalMemo(memoId);
	}
	
	public void addBoardMember(BoardMember boardMember) {
		personalMemoMapper.addBoardMember(boardMember);
	}
	
	public void deleteBoardMember(BoardMember boardMember) {
		personalMemoMapper.deleteBoardMember(boardMember);
	}
}
