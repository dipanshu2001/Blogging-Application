package com.SpringProject.Blogging.Application.Payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Login request payload")
public class JwtAuthRequest {
    @Schema(description = "Email used to login",example = "author1@gmail.com")
    private String emailId;
    @Schema(description = "Password for login",example = "author123")
    private String password;
}
