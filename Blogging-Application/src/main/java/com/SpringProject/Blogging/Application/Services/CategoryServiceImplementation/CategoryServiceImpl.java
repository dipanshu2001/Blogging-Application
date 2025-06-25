package com.SpringProject.Blogging.Application.Services.CategoryServiceImplementation;

import com.SpringProject.Blogging.Application.Exceptions.ResourceNotFoundException;
import com.SpringProject.Blogging.Application.Models.Category;
import com.SpringProject.Blogging.Application.Payloads.CategoryDTO;
import com.SpringProject.Blogging.Application.Repositories.CategoryRepo;
import com.SpringProject.Blogging.Application.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO category) {
        Category cat = this.modelMapper.map(category, Category.class);
        Category addedCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(addedCat, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, int category_Id) {
        Category cat = this.categoryRepo.findById(category_Id).orElseThrow(() -> new ResourceNotFoundException("category", "category id", category_Id));
        cat.setCategoryTitle(categoryDTO.getCategoryTitle());
        cat.setDescription(categoryDTO.getDescription());
        Category updatedCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedCat, CategoryDTO.class);

    }

    @Override
    public CategoryDTO getCategoryByID(int category_Id) {
        Category cat = this.categoryRepo.findById(category_Id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", category_Id));

        return this.modelMapper.map(cat, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        return categories.stream()
                .map(cat -> this.modelMapper.map(cat, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(int category_Id) {
        Category cat = this.categoryRepo.findById(category_Id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", category_Id));

        this.categoryRepo.delete(cat);
    }

}
