package com.krince.memegle.domain.auth.repository;

import com.krince.memegle.domain.auth.entity.EmailAuthentication;
import com.krince.memegle.global.constant.AuthenticationType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailAuthenticationRepository extends CrudRepository<EmailAuthentication, String> {

    Optional<EmailAuthentication> findByEmailAndAuthenticationType(String email, AuthenticationType authenticationType);
}
