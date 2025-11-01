package com.SpringProject.Blogging.Application.Controllers;

import com.SpringProject.Blogging.Application.Payloads.ApiResponse;
import com.SpringProject.Blogging.Application.Payloads.CommentDTO;
import com.SpringProject.Blogging.Application.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // Create comment - Any authenticated user can comment
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable int postId) {
        CommentDTO createdComment = this.commentService.createComment(commentDTO, postId);
        return new ResponseEntity<CommentDTO>(createdComment, HttpStatus.CREATED);
    }

    // Delete comment - Only ADMIN and AUTHOR can delete comments
    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN')")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully!", true), HttpStatus.OK);
    }
}
