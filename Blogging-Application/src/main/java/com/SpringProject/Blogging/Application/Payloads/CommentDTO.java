package com.SpringProject.Blogging.Application.Payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Comment Data Transfer Object")
public class CommentDTO {
    @Schema(description = "Comment id",example="5")
    private int commentId;
    @Schema(description = "Comment content text",example = "Great article! Very helpful.")
    private String content;
}
