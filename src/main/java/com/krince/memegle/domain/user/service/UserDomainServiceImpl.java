package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.domain.user.entity.User;
import com.krince.memegle.domain.user.repository.SelfAuthenticationRepository;
import com.krince.memegle.domain.user.repository.UserRepository;
import com.krince.memegle.global.exception.DuplicateUserException;
import com.krince.memegle.global.exception.DuplicationResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.krince.memegle.global.response.ExceptionResponseCode.INVALID_PASSWORD;

@Service
@RequiredArgsConstructor
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;
    private final SelfAuthenticationRepository selfAuthenticationRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserInfoDto getUserInfoDtoFromId(Long userId) {
        return userRepository.findUserInfoDtoByUserId(userId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void validateDuplicateUser(String loginId) {
        boolean isExistUser = userRepository.existsByLoginId(loginId);

        if (isExistUser) {
            throw new DuplicateUserException();
        }
    }

    @Override
    public User registUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserFromLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void validatePassword(String rawPassword, String encodedPassword) {
        boolean isMatchedPassword = passwordEncoder.matches(rawPassword, encodedPassword);

        if (!isMatchedPassword) {
            throw new BadCredentialsException(INVALID_PASSWORD.getMessage());
        }
    }

    @Override
    public User getUserFromId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void validateDuplicateNickname(String nickname) {
        boolean isDuplicateNickname = userRepository.existsByNickname(nickname);

        if (isDuplicateNickname) {
            throw new DuplicationResourceException("이미 존재하는 닉네임입니다. 닉네임: " + nickname);
        }
    }

    @Override
    public void deleteFromId(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteSelfAuthenticationFromUserId(Long userId) {
        selfAuthenticationRepository.deleteByUserId(userId);
    }

    @Override
    public User getUserFromEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void validateDuplicateEmail(String email) {
        boolean isUsedEmail = selfAuthenticationRepository.existsByEmail(email);

        if (isUsedEmail) {
            throw new DuplicateUserException();
        }
    }
}
