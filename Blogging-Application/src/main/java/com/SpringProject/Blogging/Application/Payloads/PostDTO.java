package com.SpringProject.Blogging.Application.Payloads;

import com.SpringProject.Blogging.Application.Models.Category;
import com.SpringProject.Blogging.Application.Models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
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


}
