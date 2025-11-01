package com.SpringProject.Blogging.Application.Payloads;

import lombok.Data;

import java.util.Set;

@Data
public class JwtAuthResponse {
    private String token;
}
