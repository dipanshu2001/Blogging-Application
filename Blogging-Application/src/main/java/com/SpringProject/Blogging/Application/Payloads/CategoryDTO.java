package com.SpringProject.Blogging.Application.Payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Category Data Transfer Object")

public class CategoryDTO {
    @Schema(description = "Category id",example = "1")
    private int categoryId;
    @NotEmpty
    @Size(min = 4)
    @Schema(description = "Category title",example = "Programming")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 10)
    @Schema(description = "Category description",example = "Posts related to coding, software development and skills")
    private String description;
}
