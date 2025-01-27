package com.ashish.blog.controller;

import com.ashish.blog.peyloads.ApiResponse;
import com.ashish.blog.peyloads.CommentDto;
import com.ashish.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:5500")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Create a new comment
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
        @PathVariable("postId") Integer postId,
        @RequestBody CommentDto comment) {

        CommentDto createdComment = this.commentService.createComment(comment, postId);
        return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);
    }

    // Delete a comment by ID
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(
        @PathVariable("commentId") Integer commentId) {

        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully!!",true),HttpStatus.OK);
    }
}
