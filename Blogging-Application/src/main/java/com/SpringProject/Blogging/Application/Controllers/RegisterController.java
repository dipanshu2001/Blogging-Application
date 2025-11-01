package com.SpringProject.Blogging.Application.Controllers;

import com.SpringProject.Blogging.Application.Payloads.UserDTO;
import com.SpringProject.Blogging.Application.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO registered = userService.registerNewUser(userDTO);
        return new ResponseEntity<>(registered, HttpStatus.CREATED);
    }
}

