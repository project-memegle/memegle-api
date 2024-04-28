package com.krince.memegle.domain.post.service;

import com.krince.memegle.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl {

    private final PostRepository postRepository;
}
