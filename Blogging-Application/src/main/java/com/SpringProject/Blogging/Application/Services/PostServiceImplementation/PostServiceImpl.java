package com.SpringProject.Blogging.Application.Services.PostServiceImplementation;

import com.SpringProject.Blogging.Application.Exceptions.ResourceNotFoundException;
import com.SpringProject.Blogging.Application.Models.Category;
import com.SpringProject.Blogging.Application.Models.Post;
import com.SpringProject.Blogging.Application.Models.User;
import com.SpringProject.Blogging.Application.Payloads.PostDTO;
import com.SpringProject.Blogging.Application.Payloads.PostResponse;
import com.SpringProject.Blogging.Application.Payloads.UserDTO;
import com.SpringProject.Blogging.Application.Repositories.CategoryRepo;
import com.SpringProject.Blogging.Application.Repositories.PostRepo;
import com.SpringProject.Blogging.Application.Repositories.UserRepo;
import com.SpringProject.Blogging.Application.Services.PostService;
import com.SpringProject.Blogging.Application.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired private PostRepo postRepo;
    @Autowired private ModelMapper modelMapper;
    @Autowired private UserRepo userRepo;
    @Autowired private CategoryRepo categoryRepo;
    @Autowired private UserService userService;  // ✅ Needed for userToDTO()

    // ✅ Convert Post → PostDTO (fixed roles)
    private PostDTO convertToDTO(Post post) {
        PostDTO dto = modelMapper.map(post, PostDTO.class);

        // ✅ FIX: Map user using userService.userToDTO()
        UserDTO userDTO = userService.userToDTO(post.getUser());
        dto.setUser(userDTO);

        // ✅ FIX: Map category normally
        dto.setCategory(modelMapper.map(post.getCategory(), com.SpringProject.Blogging.Application.Payloads.CategoryDTO.class));

        return dto;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO, int userId, int categoryId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", (long) userId));

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", (long) categoryId));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setPostDate(new Date());
        post.setImageName("default.png");
        post.setUser(user);
        post.setCategory(category);

        Post saved = postRepo.save(post);
        return convertToDTO(saved);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, int postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", (long) postId));

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());

        Post updated = postRepo.save(post);

        return convertToDTO(updated);
    }

    @Override
    public void deletePost(int postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", (long) postId));
        postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sortDir.equalsIgnoreCase("desc")
                        ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending()
        );

        Page<Post> page = postRepo.findAll(pageable);

        List<PostDTO> dtos = page.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        PostResponse response = new PostResponse();
        response.setContent(dtos);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }

    @Override
    public PostDTO getPostById(int postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", (long) postId));
        return convertToDTO(post);
    }

    @Override
    public PostResponse getPostsByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", (long) categoryId));

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sortDir.equalsIgnoreCase("desc")
                        ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending()
        );

        Page<Post> page = postRepo.findByCategory(category, pageable);

        List<PostDTO> dtos = page.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        PostResponse response = new PostResponse();
        response.setContent(dtos);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }

    @Override
    public PostResponse getPostsByUser(int userId, int pageNumber, int pageSize, String sortBy, String sortDir) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", (long) userId));

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sortDir.equalsIgnoreCase("desc")
                        ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending()
        );

        Page<Post> page = postRepo.findByUser(user, pageable);

        List<PostDTO> dtos = page.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        PostResponse response = new PostResponse();
        response.setContent(dtos);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Post> posts = postRepo.findByTitleContaining(keyword);
        return posts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
