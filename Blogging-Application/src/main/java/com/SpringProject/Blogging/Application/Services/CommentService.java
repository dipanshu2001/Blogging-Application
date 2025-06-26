package com.SpringProject.Blogging.Application.Services;

import com.SpringProject.Blogging.Application.Payloads.CommentDTO;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO,int postId);
    void deleteComment(int commentId);

}
