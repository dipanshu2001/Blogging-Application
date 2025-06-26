package com.SpringProject.Blogging.Application.Payloads;

import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private int commentId;
    private String content;
}
