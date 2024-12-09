package com.krince.memegle.domain.user.repository.fake;

import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.domain.user.entity.User;
import com.krince.memegle.domain.user.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

public class FakeUserRepository implements UserRepository {

    private Map<Long, User> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public boolean existsByLoginId(String loginId) {
        return store.values()
                .stream()
                .anyMatch(user -> user.getLoginId().equals(loginId));
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return store.values()
                .stream()
                .anyMatch(user -> user.getNickname().equals(nickname));
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return store.values()
                .stream()
                .filter(user -> user.getLoginId().equals(loginId))
                .findFirst();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        User user = entity;

        if (entity.getId() == null) {
            User.builder()
                    .id(++sequence)
                    .loginId(entity.getLoginId())
                    .password(entity.getPassword())
                    .nickname(entity.getNickname())
                    .role(entity.getRole())
                    .createdAt(LocalDateTime.now())
                    .modifiedAt(LocalDateTime.now())
                    .build();
        }

        store.put(user.getId(), user);

        return (S) user;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Long aLong) {
        return null;
    }

    @Override
    public User getById(Long aLong) {
        return null;
    }

    @Override
    public User getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends User> S save(S entity) {
        User user = User.builder()
                .id(++sequence)
                .loginId(entity.getLoginId())
                .password(entity.getPassword())
                .nickname(entity.getNickname())
                .role(entity.getRole())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        store.put(user.getId(), user);

        return (S) user;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return store.values()
                .stream()
                .filter(user -> user.getId().equals(aLong))
                .findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<User> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        store.remove(aLong);
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {
        store.clear();
        sequence = 0L;
    }

    @Override
    public List<User> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<UserInfoDto> findUserInfoDtoByUserId(Long userId) {
        if (userId.equals(1L)) {
            return Optional.of(
                    UserInfoDto.builder()
                            .loginId("loginId")
                            .email("email@email.com")
                            .nickname("nickname")
                            .build()
            );
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email.equals("test@test.com")) {
            return Optional.of(
                    store.get(1L)
            );
        }
        return Optional.empty();
    }
}
