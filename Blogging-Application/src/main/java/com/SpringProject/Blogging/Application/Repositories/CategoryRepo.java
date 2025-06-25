package com.SpringProject.Blogging.Application.Repositories;

import com.SpringProject.Blogging.Application.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
