package com.ashish.blog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashish.blog.entities.Category;
import com.ashish.blog.entities.Post;
import com.ashish.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);

	List<Post> findByCategory(Category category);

	// Pagination methods for User and Category
	Page<Post> findByUser(User user, Pageable pageable);

	Page<Post> findByCategory(Category category, Pageable pageable);
	
	// search 	
	//List<Post> findByTitleContaining(String title);
	
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key")String title);
	
}
