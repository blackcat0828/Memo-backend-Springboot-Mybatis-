package com.jeffworld.memo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jeffworld.memo.dto.PersonalBoard;

@Mapper
public interface PersonalMemoMapper {
	
	List<PersonalBoard> findPersonalBoardByEmail(String email);
}
