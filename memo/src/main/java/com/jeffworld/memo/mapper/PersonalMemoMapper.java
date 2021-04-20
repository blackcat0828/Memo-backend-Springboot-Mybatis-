package com.jeffworld.memo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jeffworld.memo.dto.Criteria;
import com.jeffworld.memo.dto.PersonalBoard;
import com.jeffworld.memo.dto.PersonalMemo;

@Mapper
public interface PersonalMemoMapper {
	
	List<PersonalBoard> findPersonalBoardByEmail(String email);
	void addPersonalBoard(PersonalBoard board);
	void updatePersonalBoard(PersonalBoard board);
	void deletePersonalBoard(int pboardid);
	int getPersonalMemosLength(int pboardid);
	List<PersonalMemo> getPersonalMemos(Criteria criteria);
	void addPersonalMemo(PersonalMemo memo);
	void updatePersonalMemo(PersonalMemo memo);
	void deletePersonalMemo(int memoId);
	PersonalMemo getPersonalMemo(int memoId);
}
