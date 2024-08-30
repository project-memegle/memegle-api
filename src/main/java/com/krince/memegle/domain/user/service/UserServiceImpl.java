package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.domain.user.entity.User;
import com.krince.memegle.domain.user.repository.UserRepository;
import com.krince.memegle.global.Role;
import com.krince.memegle.global.exception.DuplicateUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpDto signUpDto) {
        validateDuplicateUser(signUpDto.getLoginId());

        Role role = Role.ROLE_USER;
        User user = signUpDtoToUser(signUpDto, role);

        userRepository.save(user);
    }

    private void validateDuplicateUser(String loginId) {
        boolean isExistUser = userRepository.existsByLoginId(loginId);

        if (isExistUser) {
            throw new DuplicateUserException();
        }
    }

    private User signUpDtoToUser(SignUpDto signUpDto, Role role) {
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

        return User.builder()
                .loginId(signUpDto.getLoginId())
                .password(encodedPassword)
                .name(signUpDto.getName())
                .nickname(signUpDto.getNickname())
                .role(role)
                .build();
    }
}
