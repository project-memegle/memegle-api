package com.krince.memegle.client.domain.post.repository;

import com.krince.memegle.client.domain.post.dto.response.ResponsePostListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.krince.memegle.client.domain.image.entity.QImage.*;
import static com.krince.memegle.client.domain.post.entity.QPost.*;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ResponsePostListDto> findAllByIsConfirm(boolean isConfirm) {
        return queryFactory
                .select(Projections.constructor(ResponsePostListDto.class))
                .from(post)
                .leftJoin(image).on(image.post.eq(post))
                .fetchJoin()
                .where(post.isConfirm.eq(isConfirm))
                .fetch();
    }

}
