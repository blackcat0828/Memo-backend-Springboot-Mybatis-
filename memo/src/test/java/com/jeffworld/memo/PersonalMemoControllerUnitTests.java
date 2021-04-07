package com.jeffworld.memo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
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
	
	//보드 카테고리 리스트 확인
	@Test
	public void getBoardList() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("owner", "admin@test.com");
		String json = gson.toJson(obj);
		mock.perform(get("/boards").contentType(MediaType.APPLICATION_JSON).content(json))
		.andDo(print())
		.andExpect(status().isOk()).andExpect(jsonPath("$[1].contents").value("테스트메모2 입니다."));
		
	}
}
