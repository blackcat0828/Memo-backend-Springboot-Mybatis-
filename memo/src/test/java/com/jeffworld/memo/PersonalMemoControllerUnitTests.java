package com.jeffworld.memo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jeffworld.memo.controller.PersonalMemoController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonalMemoControllerUnitTests {
private Gson gson = new Gson();
	
	@Autowired
	private PersonalMemoController personalMemoController;
	
	private MockMvc mock;
	
	@Before
	public void setUp() throws Exception{
		mock = MockMvcBuilders.standaloneSetup(personalMemoController).build();
	}
	
	//보드 카테고리 리스트 확인 - RequestParam으로 바꿧음으로 추후 테스트 메소드 수정
	@Ignore
	public void getBoardList() throws Exception {
		mock.perform(get("/boards/personal").contentType(MediaType.APPLICATION_JSON).param("email", "admin@admin.com"))
		.andDo(print())
		.andExpect(status().isOk());
		
	}
	
	//메모장 추가 테스트
	@Ignore
	public void registerBoardTest() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("owner", "admin@admin.com");
		obj.addProperty("title", "개인보드 selectKey Test");
		String json = gson.toJson(obj);
		
		mock.perform(post("/boards/personal").contentType(MediaType.APPLICATION_JSON)
		.content(json)).andExpect(status().isCreated()).andReturn();
	}
	
	//보드 제목 변경
	@Ignore
	public void updateBoardTest() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("title", "수정 테스트1");
		String json = gson.toJson(obj);
		
		mock.perform(put("/boards/personal/1").contentType(MediaType.APPLICATION_JSON)
		.content(json)).andDo(print()).andExpect(status().isOk());
	}
	
	@Ignore
	public void deleteBoardTest() throws Exception {

		
		mock.perform(delete("/boards/personal/1").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk());
	
	}
	
	@Ignore
	public void getMemoList() throws Exception {

		mock.perform(get("/boards/personal/2").contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk());
		
	}
	
	//메모 추가 테스트
	@Ignore
	public void addPersonalMemoTest() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("title", "메모 추가 테스트");
		obj.addProperty("contents", "메모 추가 테스트1");
		String json = gson.toJson(obj);
		
		mock.perform(post("/boards/personal/2/memos").contentType(MediaType.APPLICATION_JSON)
		.content(json)).andDo(print()).andExpect(status().isCreated());
	}
	
	//특정 메모 가져오기
	@Test
	public void getMemoTest() throws Exception {

		
		mock.perform(get("/boards/personal/2/memos/5").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk());
	
	}
	
	//메모 Update
	@Ignore
	public void updateMemoTest() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("title", "메모 수정 테스트1");
		obj.addProperty("contents", "메모 수정 테스트2");
		String json = gson.toJson(obj);
		
		mock.perform(put("/boards/personal/2/memos/1").contentType(MediaType.APPLICATION_JSON)
		.content(json)).andDo(print()).andExpect(status().isOk());
	}
	
	@Ignore
	public void deleteMemoTest() throws Exception {

		
		mock.perform(delete("/boards/personal/2/memos/2").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk());
	
	}
}
