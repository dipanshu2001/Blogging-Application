package com.SpringProject.Blogging.Application.Services;

import com.SpringProject.Blogging.Application.Payloads.CategoryDTO;
import com.SpringProject.Blogging.Application.Payloads.UserDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO updateCategory(CategoryDTO category, int category_Id);

    CategoryDTO getCategoryByID(int category_Id);

    List<CategoryDTO> getAllCategories();

    void deleteCategory(int category_Id);
}
