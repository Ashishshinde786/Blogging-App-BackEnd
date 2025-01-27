package com.ashish.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ashish.blog.entities.Category;
import com.ashish.blog.entities.Post;
import com.ashish.blog.entities.User;
import com.ashish.blog.exceptions.ResourceNotFoundException;
import com.ashish.blog.peyloads.PostDto;
import com.ashish.blog.peyloads.PostResponse;
import com.ashish.blog.repositories.CategoryRepo;
import com.ashish.blog.repositories.PostRepo;
import com.ashish.blog.repositories.UserRepo;
import com.ashish.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);

		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// Fetch the existing post
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

		// Update the fields
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setAddedDate(postDto.getAddedDate()); // Optional, only if you want to update the date
		post.setCategory(this.modelMapper.map(postDto.getCategory(), Category.class));
		post.setUser(this.modelMapper.map(postDto.getUser(), User.class));

		// Save the updated post
		Post updatedPost = this.postRepo.save(post);

		// Map the updated post to PostDto and return
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		// Fetch the post to check if it exists
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

		// Delete the post
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		// int pageSize = 5;
		// int pageNumber = 1;

		Sort sort = null;

		// Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() :
		// Sort.by(sortBy).descending();

		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}

		PageRequest p = PageRequest.of(pageNumber, pageSize, sort);

//		PageRequest p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());

		Page<Post> pagePost = this.postRepo.findAll(p);

		List<Post> allPosts = pagePost.getContent();

		List<Post> allposts = this.postRepo.findAll(); // Retrieve all posts from the repository
		List<PostDto> postDtos = allposts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList()); // Map to PostDto and collect into a list

		PostResponse postResponse = new PostResponse();

		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId)); // Find post or throw
																								// exception
		return this.modelMapper.map(post, PostDto.class); // Map to PostDto
	}

	// @Override
	// public List<PostDto> getPostsByCategory(Integer categoryId) {
	// Category cat = this.categoryRepo.findById(categoryId)
	// .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id",
	// categoryId));
	// List<Post> posts = this.postRepo.findByCategory(cat);
	// List<PostDto> postDtos = posts.stream().map((post) ->
	// this.modelMapper.map(post, PostDto.class))
	// .collect(Collectors.toList());

	// return postDtos;
	// }
	@Override
	public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost = this.postRepo.findByCategory(category, pageRequest);

		List<PostDto> postDtos = pagePost.getContent().stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	// @Override
	// public List<PostDto> getPostsByUsers(Integer userId) {
	// User user = this.userRepo.findById(userId)
	// .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

	// List<Post> posts = this.postRepo.findByUser(user);

	// List<PostDto> postDtos = posts.stream().map((post) ->
	// this.modelMapper.map(post, PostDto.class))
	// .collect(Collectors.toList());

	// return postDtos;
	// }

	@Override
	public PostResponse getPostsByUsers(Integer userId, Integer pageNumber, Integer pageSize) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost = this.postRepo.findByUser(user, pageRequest);

		List<PostDto> postDtos = pagePost.getContent().stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		// TODO Auto-generated method stub
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDto = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDto;
	}

}
