package com.SpringProject.Blogging.Application.Controllers;

import com.SpringProject.Blogging.Application.Payloads.ApiResponse;
import com.SpringProject.Blogging.Application.Payloads.CommentDTO;
import com.SpringProject.Blogging.Application.Services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Create comment on a post (Authenticated)",
            description = "Any logged in user can create comment on a post.")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable int postId) {
        CommentDTO created = commentService.createComment(commentDTO, postId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Delete comment - Only ADMIN and AUTHOR can delete comments
    @Operation(summary = "Delete comment (Author / Admin)",
            description = "Only users with role AUTHOR or ADMIN can delete any comment.")
    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN')")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(new ApiResponse("Comment deleted successfully!", true));
    }
}
