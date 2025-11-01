package com.SpringProject.Blogging.Application.Services.CommentServiceImplementation;

import com.SpringProject.Blogging.Application.Exceptions.ResourceNotFoundException;
import com.SpringProject.Blogging.Application.Models.Comments;
import com.SpringProject.Blogging.Application.Models.Post;
import com.SpringProject.Blogging.Application.Payloads.CommentDTO;
import com.SpringProject.Blogging.Application.Repositories.CommentRepo;
import com.SpringProject.Blogging.Application.Repositories.PostRepo;
import com.SpringProject.Blogging.Application.Services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDTO createComment(CommentDTO commentDTO, int postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","post id", (long) postId));
        Comments comments=this.modelMapper.map(commentDTO, Comments.class);
        comments.setPost(post);
        Comments savedComment=this.commentRepo.save(comments);
        return this.modelMapper.map(savedComment,CommentDTO.class);
    }

    @Override
    public void deleteComment(int commentId) {
        Comments com=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","comment id", (long) commentId));
        this.commentRepo.delete(com);

    }
}
