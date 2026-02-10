package com.SpringProject.Blogging.Application.Controllers;

import com.SpringProject.Blogging.Application.Payloads.UserDTO;
import com.SpringProject.Blogging.Application.Services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@Tag(name="Registration",description = "User registration API")
@RestController
@RequestMapping("/api/v1/register")
public class RegisterController {

    @Autowired private UserService userService;
    @Operation(summary = "Register a new user",
            description = "Register new user. Roles may be provided (e.g. [\"ADMIN\",\"AUTHOR\"]). If omitted, USER role is assigned.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered"),
                    @ApiResponse(responseCode = "400", description = "Validation error")
            })
    @PostMapping
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO created = userService.registerNewUser(userDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}

