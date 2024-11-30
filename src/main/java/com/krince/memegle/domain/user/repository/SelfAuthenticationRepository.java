package com.krince.memegle.domain.user.repository;

import com.krince.memegle.domain.user.entity.SelfAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfAuthenticationRepository extends JpaRepository<SelfAuthentication, Long> {}