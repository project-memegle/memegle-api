package com.krince.memegle.client.domain.like.controller;

import com.krince.memegle.client.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeControllerImpl implements LikeController {

    private final LikeService likeService;
}
