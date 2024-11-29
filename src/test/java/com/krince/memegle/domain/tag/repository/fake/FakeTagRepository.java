package com.krince.memegle.domain.tag.repository.fake;

import com.krince.memegle.domain.tag.entity.Tag;
import com.krince.memegle.domain.tag.repository.TagRepository;
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

public class FakeTagRepository implements TagRepository {

    private Map<Long, Tag> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Optional<Tag> findByTagName(String tagName) {
        return store.values()
                .stream()
                .filter(tag -> tag.getTagName().equals(tagName))
                .findFirst();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Tag> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Tag> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Tag> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Tag getOne(Long aLong) {
        return null;
    }

    @Override
    public Tag getById(Long aLong) {
        return null;
    }

    @Override
    public Tag getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Tag> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Tag> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Tag> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Tag> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Tag> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Tag> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Tag, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Tag> S save(S entity) {
        Tag tag = Tag.builder()
                .id(++sequence)
                .tagName(entity.getTagName())
                .build();

        store.put(tag.getId(), tag);

        return (S) tag;
    }

    @Override
    public <S extends Tag> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Tag> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Tag> findAll() {
        return List.of();
    }

    @Override
    public List<Tag> findAllById(Iterable<Long> longs) {
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
    public void delete(Tag entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Tag> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Tag> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Tag> findAll(Pageable pageable) {
        return null;
    }
}
