package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Component
public class Comment {
	@Id
	@GeneratedValue
	private int id;
	private String desc;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="post_id")
	private Post post;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Comment(String desc, Post post) {
		super();
		this.desc = desc;
		this.post = post;
	}
	
	public Comment(String desc) {
		super();
		this.desc = desc;
	}

	public Comment() {
		super();
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", desc=" + desc + ", post=" + post + "]";
	}
	
	
	
}
