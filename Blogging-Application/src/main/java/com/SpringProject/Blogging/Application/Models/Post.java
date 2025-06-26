package com.SpringProject.Blogging.Application.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    @Column(name = "post_title", length = 100, nullable = false)
    private String title;
    @Column(length = 10000)
    private String content;
    private String imageName;
    private Date postDate;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Comments> comments=new HashSet<>();

}
