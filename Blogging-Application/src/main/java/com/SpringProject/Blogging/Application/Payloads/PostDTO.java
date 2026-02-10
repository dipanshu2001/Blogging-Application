package com.SpringProject.Blogging.Application.Payloads;

import com.SpringProject.Blogging.Application.Models.Category;
import com.SpringProject.Blogging.Application.Models.Comments;
import com.SpringProject.Blogging.Application.Models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Post Data Transfer Object")
public class PostDTO {
    @Schema(description = "Post id",example = "2")
    private int postId;
    @Schema(description = "Post title",example = "Java vs Python – Updated")
    private String title;
    @Schema(description = "Post content",example = "Detailed comparison between Java and Python programming languages...")
    private String content;
    @Schema(description = "Image filename",example = "default.png")
    private String imageName;
    @Schema(description = "Post creation date/time", example = "2025-11-01T13:09:33.212Z")
    private Date postDate;

    @Schema(description = "Category DTO")
    private CategoryDTO category;

    @Schema(description = "Author / User DTO")
    private UserDTO user;

    @Schema(description = "List of comments")
    private Set<CommentDTO> comments = new HashSet<>();


}
