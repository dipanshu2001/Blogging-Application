package com.SpringProject.Blogging.Application.Payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Generic API response wrapper")
public class ApiResponse {
    @Schema(example = "Operation successful")
    private String message;
    @Schema(example = "true")
    private boolean success;
}
