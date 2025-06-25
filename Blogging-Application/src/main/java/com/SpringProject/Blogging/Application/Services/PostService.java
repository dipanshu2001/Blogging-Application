package com.SpringProject.Blogging.Application.Services;

import com.SpringProject.Blogging.Application.Models.Post;
import com.SpringProject.Blogging.Application.Payloads.PostDTO;
import com.SpringProject.Blogging.Application.Payloads.PostResponse;

import java.util.List;

public interface PostService {
    // Create
    PostDTO createPost(PostDTO postDTO, int userId, int categoryId);

    // Update post
    PostDTO updatePost(PostDTO postDTO, int postId);

    // Delete post
    void deletePost(int postId);

    // Get all posts
    PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);

    // Get single post
    PostDTO getPostById(int postId);

    // Get all posts by category
    PostResponse getPostsByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);

    // Get all posts by user
    PostResponse getPostsByUser(int userId, int pageNumber, int pageSize, String sortBy, String sortDir);

    List<PostDTO> searchPosts(String keyword);

}
