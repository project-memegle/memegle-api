package com.krince.memegle.domain.user.repository;

import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserQueryRepository {

    Optional<UserInfoDto> findUserInfoDtoByUserId(Long userId);
}
