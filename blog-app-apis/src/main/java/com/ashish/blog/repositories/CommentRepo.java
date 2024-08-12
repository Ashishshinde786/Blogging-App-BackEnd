package com.ashish.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashish.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment , Integer>{

	
	
}
