package com.ashish.blog.services;

import java.util.List;
import com.ashish.blog.peyloads.PostDto;
import com.ashish.blog.peyloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	PostDto updatePost(PostDto postDto, Integer postId);

	void deletePost(Integer postId);

	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	PostDto getPostById(Integer postId);

	PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);

	PostResponse getPostsByUsers(Integer userId, Integer pageNumber, Integer pageSize);

	List<PostDto> searchPosts(String keyword);
}
