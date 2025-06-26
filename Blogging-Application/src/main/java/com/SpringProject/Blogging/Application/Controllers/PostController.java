
package com.SpringProject.Blogging.Application.Controllers;


import com.SpringProject.Blogging.Application.Config.AppConstants;
import com.SpringProject.Blogging.Application.Payloads.PostDTO;
import com.SpringProject.Blogging.Application.Payloads.PostResponse;
import com.SpringProject.Blogging.Application.Services.FileService;
import com.SpringProject.Blogging.Application.Services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    // Create post
    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDTO> createPost(
            @RequestBody PostDTO postDTO,
            @PathVariable int userId,
            @PathVariable int categoryId
    ) {
        PostDTO createdPost = postService.createPost(postDTO, userId, categoryId);
        return ResponseEntity.ok(createdPost);
    }

    // Get all posts with pagination and sorting
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        PostResponse postResponse = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(postResponse);
    }

    // Get posts by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<PostResponse> getPostsByCategory(
            @PathVariable int categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        PostResponse postResponse = postService.getPostsByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(postResponse);
    }

    // Get posts by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<PostResponse> getPostsByUser(
            @PathVariable int userId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        PostResponse postResponse = postService.getPostsByUser(userId, pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(postResponse);
    }

    // Update post
    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable int postId) {
        PostDTO updatedPost = postService.updatePost(postDTO, postId);
        return ResponseEntity.ok(updatedPost);
    }

    // Delete post
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("Post deleted successfully");
    }

    // Get post by id
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable int postId) {
        PostDTO postDTO = postService.getPostById(postId);
        return ResponseEntity.ok(postDTO);
    }
    // Searching
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable("keywords")String keywords){
        List<PostDTO> result=this.postService.searchPosts(keywords);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    // post image upload
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadImage(@RequestParam("image")MultipartFile image, @PathVariable int postId) throws IOException{
        PostDTO postDTO=this.postService.getPostById(postId);
        String fileName=this.fileService.uploadImage(path,image);

       postDTO.setImageName(fileName);
       PostDTO updatePost=this.postService.updatePost(postDTO,postId);
       return new ResponseEntity<PostDTO>(updatePost,HttpStatus.OK);
    }

}
