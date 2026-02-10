package com.SpringProject.Blogging.Application.Controllers;

import com.SpringProject.Blogging.Application.Payloads.CategoryDTO;
import com.SpringProject.Blogging.Application.Services.CategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Categories",
        description = "APIs for Managing Blog Categories (Admin Protected)"
)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // ✅ Create Category (ADMIN ONLY)
    @Operation(
            summary = "Create a new category",
            description = "Admin only API to create a new blog category.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Category Created Successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid Token"),
                    @ApiResponse(responseCode = "403", description = "Access Denied (Not Admin)")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO addedCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(addedCategory, HttpStatus.CREATED);
    }

    // ✅ Update Category (ADMIN ONLY)
    @Operation(
            summary = "Update existing category",
            description = "Admin only API to update category details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category Updated Successfully"),
                    @ApiResponse(responseCode = "404", description = "Category Not Found")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            @PathVariable("id") int id
    ) {
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO, id);
        return ResponseEntity.ok(updatedCategory);
    }

    // ✅ Delete Category (ADMIN ONLY)
    @Operation(
            summary = "Delete a category",
            description = "Admin only API to delete category by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category Deleted Successfully"),
                    @ApiResponse(responseCode = "404", description = "Category Not Found")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<com.SpringProject.Blogging.Application.Payloads.ApiResponse> deleteCategory(@PathVariable("id") int id) {

        categoryService.deleteCategory(id);

        return new ResponseEntity<>(
                new com.SpringProject.Blogging.Application.Payloads.ApiResponse(
                        "Category deleted successfully!",
                        true
                ),
                HttpStatus.OK
        );
    }

    // ✅ Get Single Category (AUTHENTICATED USERS)
    @Operation(
            summary = "Get category by ID",
            description = "Fetch details of a category. Auth required.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category Found"),
                    @ApiResponse(responseCode = "404", description = "Category Not Found")
            }
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") int id) {
        CategoryDTO categoryDTO = categoryService.getCategoryByID(id);
        return ResponseEntity.ok(categoryDTO);
    }

    // ✅ Get All Categories (AUTHENTICATED USERS)
    @Operation(
            summary = "Fetch all categories",
            description = "Returns list of all categories. Auth required.",
            responses = @ApiResponse(responseCode = "200", description = "Categories Fetched Successfully")
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
