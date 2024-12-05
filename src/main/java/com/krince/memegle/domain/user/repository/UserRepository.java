package com.krince.memegle.domain.user.repository;

import com.krince.memegle.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLoginId(String loginId);

    boolean existsByNickname(String nickname);

    Optional<User> findByLoginId(String loginId);
}
