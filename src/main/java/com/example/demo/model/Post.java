package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Post {
	@Id
	@GeneratedValue
	private int id;
	private String title;
	@OneToMany(mappedBy = "post", cascade = {CascadeType.ALL})
	private List<Comment> comments;
	
	public Post(String title, List<Comment> comments) {
		super();
		this.title = title;
		this.comments = comments;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
		for(Comment comment : comments) {
			comment.setPost(this);
			System.out.println("here");
		}
	}
	public Post() {
		super();
	}
	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", comments=" + comments + "]";
	}
	
	
	
}
