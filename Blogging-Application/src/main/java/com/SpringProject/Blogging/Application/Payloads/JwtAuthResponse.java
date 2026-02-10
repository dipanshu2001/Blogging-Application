package com.SpringProject.Blogging.Application.Payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
public class JwtAuthResponse {
    @Schema(description = "JWT token string",example="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}
