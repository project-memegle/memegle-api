package com.krince.memegle.domain.post.repository;

import com.krince.memegle.domain.like.entity.Like;
import com.krince.memegle.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Like> {
}
