package com.krince.memegle.domain.user.repository.fake;

import com.krince.memegle.domain.user.entity.SelfAuthentication;
import com.krince.memegle.domain.user.repository.SelfAuthenticationRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class FakeSelfAuthenticationRepository implements SelfAuthenticationRepository {

    private Map<Long, SelfAuthentication> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public void flush() {

    }

    @Override
    public <S extends SelfAuthentication> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends SelfAuthentication> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<SelfAuthentication> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public SelfAuthentication getOne(Long aLong) {
        return null;
    }

    @Override
    public SelfAuthentication getById(Long aLong) {
        return null;
    }

    @Override
    public SelfAuthentication getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends SelfAuthentication> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends SelfAuthentication> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends SelfAuthentication> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends SelfAuthentication> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends SelfAuthentication> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends SelfAuthentication> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends SelfAuthentication, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends SelfAuthentication> S save(S entity) {
        return null;
    }

    @Override
    public <S extends SelfAuthentication> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<SelfAuthentication> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<SelfAuthentication> findAll() {
        return List.of();
    }

    @Override
    public List<SelfAuthentication> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(SelfAuthentication entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends SelfAuthentication> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<SelfAuthentication> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<SelfAuthentication> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteByUserId(Long userId) {
        store.values().stream()
                .filter(selfAuthentication -> selfAuthentication.getUserId().equals(userId))
                .findFirst()
                .ifPresent(selfAuthentication -> store.remove(selfAuthentication.getId()));
    }
}
