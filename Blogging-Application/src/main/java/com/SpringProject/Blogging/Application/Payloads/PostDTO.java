package com.SpringProject.Blogging.Application.Payloads;

import com.SpringProject.Blogging.Application.Models.Category;
import com.SpringProject.Blogging.Application.Models.Comments;
import com.SpringProject.Blogging.Application.Models.User;
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

public class PostDTO {
    private int postId;
    private String title;
    private String content;
    private String imageName;
    private Date postDate;
    private CategoryDTO category;
    private UserDTO user;
    private Set<CommentDTO> comments=new HashSet<>();


}
