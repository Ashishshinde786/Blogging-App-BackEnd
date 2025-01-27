 package com.ashish.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashish.blog.entities.Comment;
import com.ashish.blog.entities.Post;
import com.ashish.blog.exceptions.ResourceNotFoundException;
import com.ashish.blog.peyloads.CommentDto;
import com.ashish.blog.repositories.CommentRepo;
import com.ashish.blog.repositories.PostRepo;
import com.ashish.blog.services.CommentService;


@Service
public class CommentServiceImpl implements CommentService {

	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId ));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
	    // Check if the comment exists
	    Comment comment = this.commentRepo.findById(commentId)
	        .orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));

	    // Delete the comment
	    this.commentRepo.delete(comment);
	}
	

}
