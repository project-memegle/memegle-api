package com.krince.memegle.admin.domain.post.repository;

import com.krince.memegle.client.domain.post.entity.Post;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminPostRepositoryTest {

    @Autowired
    AdminPostRepository adminPostRepository;

    private Post post1;
    private Post post2;
    private Post post3;
    private Post savedPost1;
    private Post savedPost2;
    private Post savedPost3;

    @BeforeEach
    public void before() {
        post1 = Post.builder().content("testContent1").isConfirm(true).build();
        post2 = Post.builder().content("testContent2").isConfirm(true).build();
        post3 = Post.builder().content("testContent3").isConfirm(false).build();

        savedPost1 = adminPostRepository.save(post1);
        savedPost2 = adminPostRepository.save(post2);
        savedPost3 = adminPostRepository.save(post3);
    }

    @Test
    @DisplayName("밈 게시물 등록 승인 유무에 따라 전체 조회 테스트")
    void findAllByIsConfirm() {
        List<Post> findTruePosts = adminPostRepository.findAllByIsConfirm(true);
        List<Post> findFalsePosts = adminPostRepository.findAllByIsConfirm(false);

        assertThat(findTruePosts.size()).isEqualTo(2);
        assertThat(findFalsePosts.size()).isEqualTo(1);
    }
}