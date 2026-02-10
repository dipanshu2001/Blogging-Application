package com.SpringProject.Blogging.Application.Payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "User Data Transfer Object")
public class UserDTO {
    private int userId;
    @NotEmpty
    @Size(min = 4, message = "username must be min of 4 characters !!")
    @Schema(description = "User,s display name",example="Dipanshu Joshi")
    private String userName;
    @Email(message = "Email address is not valid !!")
    @Schema(description="User email (unique)",example = "joshidipanshu71@gmail.com")
    private String emailId;
    @NotEmpty
    @Size(min = 3, max = 10, message = "password must be min of 3 chars and max of 10 chars !!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Password (write-only)",example = "strongPassword123")
    private String password;
    @Schema(description = "Requested roles for the user (names with ROLE_prefix)",example = "[\"USER\", \"AUTHOR\"]")
    private Set<String> roles;
}

