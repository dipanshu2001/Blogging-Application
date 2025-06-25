package com.SpringProject.Blogging.Application.Repositories;

import com.SpringProject.Blogging.Application.Models.Category;
import com.SpringProject.Blogging.Application.Models.Post;
import com.SpringProject.Blogging.Application.Models.User;
import com.SpringProject.Blogging.Application.Payloads.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    Page<Post> findByUser(User user, Pageable pageable);

    Page<Post> findByCategory(Category category, Pageable pageable);

    List<Post> findByTitleContaining(String title);

}
