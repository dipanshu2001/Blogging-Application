package com.SpringProject.Blogging.Application.Services.PostServiceImplementation;

import com.SpringProject.Blogging.Application.Exceptions.ResourceNotFoundException;
import com.SpringProject.Blogging.Application.Models.Category;
import com.SpringProject.Blogging.Application.Models.Post;
import com.SpringProject.Blogging.Application.Models.User;
import com.SpringProject.Blogging.Application.Payloads.PostDTO;
import com.SpringProject.Blogging.Application.Payloads.PostResponse;
import com.SpringProject.Blogging.Application.Repositories.CategoryRepo;
import com.SpringProject.Blogging.Application.Repositories.PostRepo;
import com.SpringProject.Blogging.Application.Repositories.UserRepo;
import com.SpringProject.Blogging.Application.Services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDTO createPost(PostDTO postDTO, int userId, int categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "user id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));
        Post post = this.modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setPostDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());

        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public void deletePost(int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Pageable p = PageRequest.of(pageNumber, pageSize,
                sortDir.equalsIgnoreCase("desc") ?
                        org.springframework.data.domain.Sort.by(sortBy).descending() :
                        org.springframework.data.domain.Sort.by(sortBy).ascending());

        Page<Post> pagePost = this.postRepo.findAll(p);
        List<PostDTO> postDTOS = pagePost.getContent().stream()
                .map(post -> this.modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }


    @Override
    public PostDTO getPostById(int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        return this.modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PostResponse getPostsByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sortDir.equalsIgnoreCase("desc") ?
                        org.springframework.data.domain.Sort.by(sortBy).descending() :
                        org.springframework.data.domain.Sort.by(sortBy).ascending()
        );

        Page<Post> pagePost = this.postRepo.findByCategory(cat, pageable);

        List<PostDTO> postDTOS = pagePost.getContent().stream()
                .map(post -> this.modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }



    @Override
    public PostResponse getPostsByUser(int userId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sortDir.equalsIgnoreCase("desc") ?
                        org.springframework.data.domain.Sort.by(sortBy).descending() :
                        org.springframework.data.domain.Sort.by(sortBy).ascending()
        );

        Page<Post> pagePost = this.postRepo.findByUser(user, pageable);

        List<PostDTO> postDTOS = pagePost.getContent().stream()
                .map(post -> this.modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Post> posts= this.postRepo.findByTitleContaining(keyword);
        List<PostDTO> postDTOS=posts.stream().map(post -> this.modelMapper.map(post,PostDTO.class)).collect(Collectors.toList());
        return postDTOS;
    }
}
