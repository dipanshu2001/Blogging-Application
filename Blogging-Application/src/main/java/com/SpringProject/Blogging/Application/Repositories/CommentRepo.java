package com.SpringProject.Blogging.Application.Repositories;

import com.SpringProject.Blogging.Application.Models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository <Comments,Integer>{
}
