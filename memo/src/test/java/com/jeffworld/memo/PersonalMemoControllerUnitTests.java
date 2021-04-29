package com.jeffworld.memo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jeffworld.memo.controller.PersonalMemoController;


@SpringBootTest
@AutoConfigureMockMvc
public class PersonalMemoControllerUnitTests {
private Gson gson = new Gson();
	
	@Autowired
	private PersonalMemoController personalMemoController;
	
	private MockMvc mock;
	
	@BeforeEach
	public void setUp() throws Exception{
		mock = MockMvcBuilders.standaloneSetup(personalMemoController).build();
	}
	
	//내가 주인인 보드 리스트 확인
	@Disabled
	public void getBoardList() throws Exception {
		mock.perform(get("/boards/personal").contentType(MediaType.APPLICATION_JSON).param("email", "admin@admin.com"))
		.andDo(print())
		.andExpect(status().isOk());
		
	}
	
	//내가 맴버인 보드 리스트 확인
	@Disabled
	public void getTeamBoardList() throws Exception {
		mock.perform(get("/boards/member").contentType(MediaType.APPLICATION_JSON).param("email", "member@member.com"))
		.andDo(print())
		.andExpect(status().isOk());
		
	}
	
	//메모장 추가 테스트
	@Disabled
	public void registerBoardTest() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("owner", "admin@admin.com");
		obj.addProperty("title", "개인보드 selectKey Test");
		String json = gson.toJson(obj);
		
		mock.perform(post("/boards/personal").contentType(MediaType.APPLICATION_JSON)
		.content(json)).andExpect(status().isCreated()).andReturn();
	}
	
	//보드 제목 변경
	@Disabled
	public void updateBoardTest() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("title", "수정 테스트1");
		String json = gson.toJson(obj);
		
		mock.perform(put("/boards/personal/1").contentType(MediaType.APPLICATION_JSON)
		.content(json)).andDo(print()).andExpect(status().isOk());
	}
	
	//개인 보드 삭제
	@Disabled
	public void deleteBoardTest() throws Exception {

		
		mock.perform(delete("/boards/personal/1").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk());
	
	}
	
	//메모 리스트 가져오기 Criteria 없음
	@Disabled
	public void getMemoList() throws Exception {

		mock.perform(get("/boards/personal/2").contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk());
		
	}
	
	//메모 추가 테스트
	@Disabled
	public void addPersonalMemoTest() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("title", "메모 추가 테스트");
		obj.addProperty("contents", "메모 추가 테스트1");
		String json = gson.toJson(obj);
		
		mock.perform(post("/boards/personal/2/memos").contentType(MediaType.APPLICATION_JSON)
		.content(json)).andDo(print()).andExpect(status().isCreated());
	}
	
	//메모 대량 생성
//	@Disabled
//	public void addPersonalMemoMany() throws Exception {
//			JsonObject obj = new JsonObject();
//		for(int i = 0; i<100; i++) {
//			obj.addProperty("title", "검색 테스트"+i);
//			obj.addProperty("contents", "검색 테스트"+i);
//			obj.addProperty("creator", "admin@admin.com");
//			String json = gson.toJson(obj);
//		mock.perform(post("/boards/personal/2/memos").contentType(MediaType.APPLICATION_JSON)
//		.content(json)).andDo(print()).andExpect(status().isCreated());
//		}
//	}
	
	//메모 갯수 가져오기
	@Disabled
	public void getMemoLengthTest() throws Exception {
		
		
		mock.perform(get("/boards/personal/2/length").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk());
	
	}
	
	//제목으로 검색된 메모 갯수 가져오기
	@Disabled
	public void getMemoLengthWithTitleTest() throws Exception {
		
		
		mock.perform(get("/boards/personal/1/search/length?title=메모").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk());
	
	}
	
	//제목으로 검색된 개인 메모 가져오기
	@Disabled
	public void getMemoListsWithTitleTest() throws Exception {
		
		
		mock.perform(get("/boards/personal/2/search?title=검색&&perPage=9&&currentPage=1").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk());
	
	}
	
	//메모 리스트 가져오기
	@Disabled
	public void getMemoListsTest() throws Exception {

		
		mock.perform(get("/boards/personal/2?perPage=9&&currentPage=2").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk());
	
	}
	
	//특정 메모 정보 가져오기
	@Disabled
	public void getMemoTest() throws Exception {

		
		mock.perform(get("/boards/personal/2/memos/5").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk());
	
	}
	
	//메모 수정
	@Disabled
	public void updateMemoTest() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("title", "메모 수정 테스트1");
		obj.addProperty("contents", "메모 수정 테스트2");
		String json = gson.toJson(obj);
		
		mock.perform(put("/boards/personal/2/memos/1").contentType(MediaType.APPLICATION_JSON)
		.content(json)).andDo(print()).andExpect(status().isOk());
	}
	
	//메모 삭제
	@Disabled
	public void deleteMemoTest() throws Exception {

		
		mock.perform(delete("/boards/personal/2/memos/2").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk());
	
	}
	
	//보드 맴버 추가
	@Disabled
	public void addBoardMemberTest() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("boardMember", "test2@test.com");

		String json = gson.toJson(obj);
		
		mock.perform(post("/boards/personal/2/member").contentType(MediaType.APPLICATION_JSON).content(json))
		.andDo(print()).andExpect(status().isCreated());
	
	}
	
	//보드 맴버 삭제
	@Disabled
	public void deleteBoardMemberTest() throws Exception {

		
		mock.perform(delete("/boards/personal/2/member").contentType(MediaType.APPLICATION_JSON).param("email", "member@member.com"))
		.andDo(print()).andExpect(status().isOk());
	
	}
}
