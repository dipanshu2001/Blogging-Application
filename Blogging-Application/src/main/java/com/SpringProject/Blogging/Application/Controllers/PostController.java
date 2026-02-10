package com.SpringProject.Blogging.Application.Controllers;

import com.SpringProject.Blogging.Application.Config.AppConstants;
import com.SpringProject.Blogging.Application.Payloads.ApiResponse;
import com.SpringProject.Blogging.Application.Payloads.PostDTO;
import com.SpringProject.Blogging.Application.Payloads.PostResponse;
import com.SpringProject.Blogging.Application.Services.FileService;
import com.SpringProject.Blogging.Application.Services.PostService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Posts", description = "Post management APIs (Author + Admin for create/update/delete)")
@RestController
@RequestMapping("/api/posts")
@SecurityRequirement(name = "bearerAuth")
public class PostController {

    @Autowired private PostService postService;
    @Autowired private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Operation(
            summary = "Create a post (Author/Admin)",
            description = "Create new blog post. Requires AUTHOR or ADMIN role.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201", description = "Post created successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401", description = "Unauthorized"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403", description = "Access denied")
            }
    )
    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN')")
    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDTO> createPost(
            @RequestBody PostDTO postDTO,
            @PathVariable int userId,
            @PathVariable int categoryId
    ) {
        PostDTO created = postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all posts (Authenticated)",
            description = "Supports pagination, sorting.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200", description = "Posts fetched successfully")
            }
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR) String sortDir
    ) {
        PostResponse res = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "Get post by ID", description = "Authenticated users only")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable int postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @Operation(summary = "Update a post (Author/Admin)")
    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN')")
    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(
            @RequestBody PostDTO postDTO,
            @PathVariable int postId
    ) {
        return ResponseEntity.ok(postService.updatePost(postDTO, postId));
    }

    @Operation(summary = "Delete post (Author/Admin)")
    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(new ApiResponse("Post deleted successfully", true));
    }

    @Operation(summary = "Search posts by title (Authenticated)")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable String keywords) {
        return ResponseEntity.ok(postService.searchPosts(keywords));
    }

    @Operation(summary = "Upload image for a post (Author/Admin)")
    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN')")
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable int postId
    ) throws IOException {

        PostDTO postDTO = postService.getPostById(postId);

        String fileName = fileService.uploadImage(path, image);
        postDTO.setImageName(fileName);

        PostDTO updated = postService.updatePost(postDTO, postId);

        return ResponseEntity.ok(updated);
    }
}
