package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.PostRepository;

@RestController
public class PostController {
	
	
	  @Autowired PostRepo postRepository;
	  
	  @GetMapping("/posts") public List<Post> retrievePosts(){ return
	  postRepository.findAll(); }
	  
	  @PostMapping("/posts") public ResponseEntity<Object> createPost(@RequestBody
	  Post post){ if(post == null) { return ResponseEntity.noContent().build(); }
	  Post newPost = postRepository.save(post); URI location =
	  ServletUriComponentsBuilder.fromCurrentRequest()
	  .path("/{id}").buildAndExpand(newPost.getId()).toUri();
	  System.out.println(location); return
	  ResponseEntity.created(location).build(); }
	  
	  @GetMapping("/posts/{id}") public Optional<Post>
	  retrievePost(@PathVariable("id") int id){ return postRepository.findById(id);
	  }
	  
	  @DeleteMapping("/posts/{id}") public ResponseEntity<Object>
	  deletePost(@PathVariable("id") int id){ postRepository.deleteById(id); return
	  ResponseEntity.noContent().build(); }
	  
	  @PutMapping("/posts/{id}") public ResponseEntity<Object>
	  updatePost(@PathVariable("id") int id, @RequestBody Post post){ if(post ==
	  null) { return ResponseEntity.noContent().build(); } post.setId(id); Post
	  newPost = postRepository.save(post); URI location =
	  ServletUriComponentsBuilder.fromCurrentRequest()
	  .path("/{id}").buildAndExpand(newPost.getId()).toUri();
	  
	  return ResponseEntity.created(location).build(); }
	 
}
