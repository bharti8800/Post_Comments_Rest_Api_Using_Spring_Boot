package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepo;


@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
class PostControllerTest {

	@MockBean
	PostRepo postRepository;
	
	@Autowired
	MockMvc mockMvc;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRetrievePosts() throws Exception {
		//setup
		List<Post> posts = new ArrayList<Post>();
		Post p1 = new Post(7, "humanity");
		Post p2 = new Post(8, "environment");
		
		posts.add(p1);
		posts.add(p2);
		
		Mockito.when(postRepository.findAll()).thenReturn(posts);
		
		//invocation
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/posts").accept(org.springframework.http.MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		
		//assertion
		
		JSONAssert.assertEquals("[{\"id\":7,\"title\":\"humanity\",\"comments\":null},{\"id\":8,\"title\":\"environment\",\"comments\":null}]"
				,result.getResponse().getContentAsString() , false);
				
		//System.out.println(result.getResponse().getContentAsString());

	}

	@Test
	void testRetrievePost() throws Exception {
		//setup
		Optional<Post> p1 = Optional.of(new Post(7, "humanity"));
		
		Mockito.when(postRepository.findById(Mockito.anyInt())).thenReturn(p1);
		
		//invocation
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/posts/4").accept(org.springframework.http.MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		
		//assertion
		
		JSONAssert.assertEquals("{\"id\":7,\"title\":\"humanity\",\"comments\":null}"
				,result.getResponse().getContentAsString() , false);
				
		//System.out.println(result.getResponse().getContentAsString());

	}
	
	@Test
	public void testCreatePost() throws Exception {
		//setup
		Post p1 = new Post(7, "humanity");
		String post1 = "{\"id\":7,\"title\":\"humanity\",\"comments\":null}";
		Mockito.when(postRepository.save(p1)).thenReturn(p1);
		
		//invocation
		RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/posts").accept(org.springframework.http.MediaType.APPLICATION_JSON).content(post1).contentType(org.springframework.http.MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		
		//assertion
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertTrue(response.getHeader(HttpHeaders.LOCATION).contains("/posts/7"));

	}
	
}
