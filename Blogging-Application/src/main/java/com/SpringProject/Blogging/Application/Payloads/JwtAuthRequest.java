package com.SpringProject.Blogging.Application.Payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String emailId;
    private String password;
}
