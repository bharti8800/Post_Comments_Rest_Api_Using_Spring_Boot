package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Post;
//@RepositoryRestResource(path = "posts", collectionResourceRel = "posts")
public interface PostRepository {

}
