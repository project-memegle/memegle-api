package com.krince.memegle.admin.domain.post.repository;

import com.krince.memegle.client.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminPostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByIsConfirm(boolean isConfirm);
}
