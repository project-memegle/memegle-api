package com.krince.memegle.domain.like.service;

import com.krince.memegle.domain.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl {

    private final LikeRepository likeRepository;
}
