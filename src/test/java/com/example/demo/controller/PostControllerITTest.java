package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.json.JSONException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.PostCommentRestApiApplication;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PostCommentRestApiApplication.class, 
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerITTest {

	@LocalServerPort
	private int port;
	
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
	public void retrievePostTest() throws JSONException {
		String url = "http://localhost:" + port + "/posts/2001";
		TestRestTemplate restTemplate = new TestRestTemplate();
		String output = restTemplate.getForObject(url, String.class);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON) );
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.GET, entity, String.class);
		String expected = "{\"id\":2001,\"title\":\"post2\"}\r\n";
		System.out.println(response.getBody());
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	public void retrievePostsTest() throws JSONException {
		String url = "http://localhost:" + port + "/posts";
		TestRestTemplate restTemplate = new TestRestTemplate();
		String output = restTemplate.getForObject(url, String.class);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON) );
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.GET, entity, String.class);
		String expected = "[{\"title\":\"post2\"},{\"title\":\"post1\"}\r\n]";
		System.out.println(response.getBody());
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	public void createPostTest() {
		String url = "http://localhost:" + port +"/posts";
		
		Post post = new Post("I am a post",Arrays.asList(new Comment("comment1")));
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON) );
		//headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Post> entity = new HttpEntity<Post>(post, headers);
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.POST, entity, String.class);
		
		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
			
		assertTrue(actual.contains("/posts/"));
	}

}
