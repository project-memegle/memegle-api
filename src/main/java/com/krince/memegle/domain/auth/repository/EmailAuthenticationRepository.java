package com.krince.memegle.domain.auth.repository;

import com.krince.memegle.domain.auth.entity.EmailAuthentication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailAuthenticationRepository extends CrudRepository<EmailAuthentication, String> {
}
