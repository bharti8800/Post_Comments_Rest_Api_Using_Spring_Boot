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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	public Post(String title) {
		super();
		this.title = title;
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
		if(comments!=null) {
			for(Comment comment : comments) {
				comment.setPost(this);
				System.out.println("here");
			}
		}
	}
	public Post() {
		super();
	}
	public Post(int id, String title) {
		// TODO Auto-generated constructor stub
		super();
		this.id = id;
		this.title = title;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", comments=" + comments + "]";
	}
	
	
	
}
