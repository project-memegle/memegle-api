package com.krince.memegle.domain.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import java.util.Date;

@Entity
@Table(name = "posts")
@Getter
public class Post {

    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;
}
