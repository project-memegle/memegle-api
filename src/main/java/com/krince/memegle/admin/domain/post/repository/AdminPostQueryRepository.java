package com.krince.memegle.admin.domain.post.repository;

import com.krince.memegle.admin.domain.post.dto.response.ResponseGetAdminPostsDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.krince.memegle.client.domain.image.entity.QImage.*;
import static com.krince.memegle.client.domain.post.entity.QPost.*;

@Repository
@RequiredArgsConstructor
public class AdminPostQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ResponseGetAdminPostsDto> findAllByIsConfirm(boolean isConfirm) {
        return queryFactory
                .select(Projections.constructor(ResponseGetAdminPostsDto.class))
                .from(post)
                .leftJoin(image).on(image.post.eq(post))
                .fetchJoin()
                .where(post.isConfirm.eq(isConfirm))
                .fetch();
    }
}
