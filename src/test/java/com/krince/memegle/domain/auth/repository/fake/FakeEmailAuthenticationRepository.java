package com.krince.memegle.domain.auth.repository.fake;

import com.krince.memegle.domain.auth.entity.EmailAuthentication;
import com.krince.memegle.domain.auth.repository.EmailAuthenticationRepository;

import java.util.Optional;

public class FakeEmailAuthenticationRepository implements EmailAuthenticationRepository {
    @Override
    public <S extends EmailAuthentication> S save(S entity) {
        return null;
    }

    @Override
    public <S extends EmailAuthentication> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<EmailAuthentication> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<EmailAuthentication> findAll() {
        return null;
    }

    @Override
    public Iterable<EmailAuthentication> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(EmailAuthentication entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends EmailAuthentication> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
