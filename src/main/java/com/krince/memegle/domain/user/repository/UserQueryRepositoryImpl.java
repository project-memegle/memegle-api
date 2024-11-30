package com.krince.memegle.domain.user.repository;

import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.domain.user.entity.QSelfAuthentication;
import com.krince.memegle.domain.user.entity.QUser;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QUser user = QUser.user;
    private final QSelfAuthentication selfAuthentication = QSelfAuthentication.selfAuthentication;

    public Optional<UserInfoDto> findUserInfoDtoByUserId(Long userId) {
        ConstructorExpression<UserInfoDto> userInfoDto = Projections.constructor(UserInfoDto.class,
                user.loginId,
                selfAuthentication.email,
                user.nickname
        );

        UserInfoDto queryResult = queryFactory
                .select(userInfoDto)
                .from(user)
                .leftJoin(selfAuthentication).on(user.id.eq(selfAuthentication.userId))
                .where(user.id.eq(userId))
                .fetchFirst();

        return Optional.ofNullable(queryResult);
    }

}
