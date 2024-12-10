package com.krince.memegle.domain.user.repository;

import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.domain.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserQueryRepository {

    Optional<UserInfoDto> findUserInfoDtoByUserId(Long userId);

    Optional<User> findByEmail(String email);
}
