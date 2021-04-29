package com.jeffworld.memo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jeffworld.memo.controller.MemberRestController;


@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerUnitTests {
	private Gson gson = new Gson();
	
	@Autowired
	private MemberRestController memberRestController;
	
	private MockMvc mock;
	
	@BeforeEach
	public void setUp() throws Exception{
		mock = MockMvcBuilders.standaloneSetup(memberRestController).build();
	}
	
	//회원 중복 확인
	@Disabled
	public void isDuplicatedTest() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("email", "admin@test.com");
		String json = gson.toJson(obj);
		mock.perform(post("/auth/signup/duplicate").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}
	
	
	//회원 가입 테스트
	@Test
	public void registerMemberTest() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("email", "member@member.com");
		obj.addProperty("password", "1234");
		obj.addProperty("name", "멤버1");
		String json = gson.toJson(obj);
		
		mock.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
		.content(json)).andExpect(status().isCreated());
	}
	
	
}
