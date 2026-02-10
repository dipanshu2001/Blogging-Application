package com.SpringProject.Blogging.Application.Controllers;

import com.SpringProject.Blogging.Application.Payloads.JwtAuthRequest;
import com.SpringProject.Blogging.Application.Payloads.JwtAuthResponse;
import com.SpringProject.Blogging.Application.Security.JwtTokenHelper;
import com.SpringProject.Blogging.Application.Services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Handles user login and token generation")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired private JwtTokenHelper jwtTokenHelper;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserService userService;

    @Operation(
            summary = "Login and generate JWT token",
            description = "Use your email and password to get JWT Token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login Successful"),
                    @ApiResponse(responseCode = "401", description = "Invalid Credentials")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JwtAuthRequest request) {
        try {
            this.authenticate(request.getEmailId(), request.getPassword());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmailId());
        String token = this.jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String emailId, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(emailId, password);

        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (DisabledException e) {
            throw new RuntimeException("User Disabled!!");
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password !!");
        }
    }
}


