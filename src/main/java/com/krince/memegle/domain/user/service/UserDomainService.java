package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.domain.user.entity.User;

public interface UserDomainService {

    UserInfoDto getUserInfoDtoFromId(Long userId);

    void validateDuplicateUser(String loginId);

    User registUser(User user);

    User getUserFromLoginId(String loginId);

    void validatePassword(String rawPassword, String encodedPassword);

    User getUserFromId(Long userId);

    void validateDuplicateNickname(String nickname);

    void deleteFromId(Long userId);

    void deleteSelfAuthenticationFromUserId(Long userId);

    User getUserFromEmail(String email);
}
