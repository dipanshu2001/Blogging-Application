package com.SpringProject.Blogging.Application.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class UserDTO {
    private int userId;
    @NotEmpty
    @Size(min = 4, message = "username must be min of 4 characters !!")
    private String userName;
    @Email(message = "Email address is not valid !!")
    private String emailId;
    @NotEmpty
    @Size(min = 3, max = 10, message = "password must be min of 3 chars and max of 10 chars !!")
    private String password;
}

