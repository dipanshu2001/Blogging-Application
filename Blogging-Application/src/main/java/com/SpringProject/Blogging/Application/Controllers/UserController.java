package com.SpringProject.Blogging.Application.Controllers;

import com.SpringProject.Blogging.Application.Payloads.ApiResponse;
import com.SpringProject.Blogging.Application.Payloads.UserDTO;
import com.SpringProject.Blogging.Application.Services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "User management (Admin only)")
@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Create a new user (Admin)",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201", description = "User created"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403", description = "Access denied")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO dto) {
        return new ResponseEntity<>(userService.createUser(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing user (Admin)")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO dto, @PathVariable int id) {
        return ResponseEntity.ok(userService.updateUser(dto, id));
    }

    @Operation(summary = "Delete a user (Admin)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse("User deleted successfully!", true));
    }

    @Operation(summary = "Get user by id (Admin)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserByID(id));
    }

    @Operation(summary = "Get all users (Admin)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}

