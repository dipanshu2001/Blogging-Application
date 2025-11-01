package com.SpringProject.Blogging.Application.Controllers;

import com.SpringProject.Blogging.Application.Payloads.ApiResponse;
import com.SpringProject.Blogging.Application.Payloads.CategoryDTO;
import com.SpringProject.Blogging.Application.Services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // Create new category - Only ADMIN can create categories
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO addedCategory = this.categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(addedCategory, HttpStatus.CREATED);
    }

    // Update category - Only ADMIN can update categories
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable("id") int id) {
        CategoryDTO updatedCategory = this.categoryService.updateCategory(categoryDTO, id);
        return ResponseEntity.ok(updatedCategory);
    }

    // Delete category - Only ADMIN can delete categories
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id") int id) {
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully !!", true), HttpStatus.OK);
    }

    // Get category by ID - Any authenticated user can view
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") int id) {
        CategoryDTO categoryDTO = this.categoryService.getCategoryByID(id);
        return ResponseEntity.ok(categoryDTO);
    }

    // Get all categories - Any authenticated user can view
    @PreAuthorize("isAuthenticated()")
    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }
}