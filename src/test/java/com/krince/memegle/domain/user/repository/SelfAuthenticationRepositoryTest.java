package com.krince.memegle.domain.user.repository;

import com.krince.memegle.domain.user.entity.SelfAuthentication;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Tag("test")
@Tag("integrationTest")
@Tag("develop")
@SpringBootTest
@DisplayName("본인 인증 리포지토리(SelfAuthenticationRepository)")
class SelfAuthenticationRepositoryTest {

    @Autowired
    private SelfAuthenticationRepository selfAuthenticationRepository;

    private SelfAuthentication selfAuthentication;

    @Tag("develop")
    @Nested
    @DisplayName("회원 아이디로 삭제")
    class DeleteByUserId {

        @BeforeEach
        void beforeEach() {
            selfAuthenticationRepository.deleteAll();
            selfAuthentication = SelfAuthentication.builder()
                    .userId(1L)
                    .email("test@test.com")
                    .build();
            selfAuthenticationRepository.save(selfAuthentication);
        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @Transactional
            @DisplayName("회원 아이디가 일치하면 해당 데이터는 삭제된다.")
            void success() {
                //given
                Long userId = selfAuthentication.getUserId();

                //when
                selfAuthenticationRepository.deleteByUserId(userId);

                //then
                assertThat(selfAuthenticationRepository.count()).isEqualTo(0);
            }

            @Test
            @Transactional
            @DisplayName("회원 아이디가 일치하지 않으면 아무 일도 일어나지 않는다.")
            void notFoundUserId() {
                //given
                Long notMatchedUserId = 293874529387459872L;

                //when
                selfAuthenticationRepository.deleteByUserId(notMatchedUserId);

                //then
                assertThat(selfAuthenticationRepository.count()).isNotEqualTo(0);
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {}
    }
}