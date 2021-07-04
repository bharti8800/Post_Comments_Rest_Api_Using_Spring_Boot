package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.PostRepository;

import java.net.URI;

@RestController
public class CommentController{
	
	  @Autowired 
	  CommentRepository commentRepo;
	  @Autowired
	  PostRepo postRepo;
	  
	  @GetMapping("/posts/{pid}/comments") 
	  public List<Comment> retrieveComments(@PathVariable("pid") int pid){
		 Post post = postRepo.findById(pid).orElse(null);
		 return post.getComments();
	  }
	  
	  @GetMapping("/posts/{pid}/comments/{id}") 
	  public Optional<Comment> retrieveComment(@PathVariable("id") int id){
		  return commentRepo.findById(id); 
	  }
	  
	  @DeleteMapping("/posts/{pid}/comments/{id}") 
	  public ResponseEntity<Object> deleteComment(@PathVariable("id") int id){
		  commentRepo.deleteById(id); 
		  return ResponseEntity.noContent().build();
	  }
	  
	  @PostMapping("/posts/{pid}/comments") 
	  public ResponseEntity<Object> createComment(@RequestBody Comment comment, @PathVariable("pid") int pid){
		  Post post = postRepo.findById(pid).orElse(null);
		  if(comment == null || post == null)
			  return ResponseEntity.noContent().build();

		  comment.setPost(post);
		  Comment newComment = commentRepo.save(comment);
		 URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			      .buildAndExpand(newComment.getId()).toUri();
		  return ResponseEntity.created(location).build();
	  }
	  
	  @PutMapping("/posts/{pid}/comments/{id}") 
	  public ResponseEntity<Object> updateComment(@RequestBody Comment comment, @PathVariable("id") int id, @PathVariable("pid") int pid){
		  if(comment == null)
			  return ResponseEntity.noContent().build();
		  comment.setId(id);
		  Post post = postRepo.findById(pid).orElse(null);
		  comment.setPost(post);
		  Comment newComment = commentRepo.save(comment);
		 URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			      .buildAndExpand(newComment.getId()).toUri();
		  return ResponseEntity.created(location).build();
	  }
	 
}
