package com.ashish.blog.services;

import com.ashish.blog.peyloads.CommentDto;

public interface CommentService {

	
	CommentDto createComment(CommentDto commentDto, Integer postId);
	
	void deleteComment(Integer commentId);
}
