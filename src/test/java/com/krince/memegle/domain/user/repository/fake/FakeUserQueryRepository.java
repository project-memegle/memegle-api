package com.krince.memegle.domain.user.repository.fake;

import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.domain.user.repository.UserQueryRepository;

import java.util.Optional;

public class FakeUserQueryRepository implements UserQueryRepository {

    @Override
    public Optional<UserInfoDto> findUserInfoDtoByUserId(Long userId) {
        if (userId.equals(1L)) {
            return Optional.of(
                    UserInfoDto.builder()
                            .loginId("loginId")
                            .email("email@email.com")
                            .nickname("nickname")
                            .build()
            );
        }

        return Optional.empty();
    }
}
