package com.krince.memegle.domain.image.repository.fake;

import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.repository.ImageRepository;
import com.krince.memegle.global.constant.ImageCategory;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class FakeImageRepository implements ImageRepository {

    private Map<Long, Image> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Page<Image> findAllByImageCategory(ImageCategory imageCategory, Pageable pageable) {
        List<Image> findImages = store.values()
                .stream()
                .filter(image -> image.getImageCategory() == imageCategory)
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), findImages.size());
        List<Image> result = findImages.subList(start, end);

        return new PageImpl<>(result, pageable, findImages.size());
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Image> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Image> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Image> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Image getOne(Long aLong) {
        return null;
    }

    @Override
    public Image getById(Long aLong) {
        return null;
    }

    @Override
    public Image getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Image> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Image> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Image> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Image> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Image> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Image> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Image, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Image> S save(S entity) {
        Image image = Image.builder()
                .id(++sequence)
                .imageUrl(entity.getImageUrl())
                .imageCategory(entity.getImageCategory())
                .registStatus(entity.getRegistStatus())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        store.put(sequence, image);

        return (S) image;
    }

    @Override
    public <S extends Image> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Image> findById(Long aLong) {
        return Optional.ofNullable(store.get(aLong));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Image> findAll() {
        return List.of();
    }

    @Override
    public List<Image> findAllById(Iterable<Long> longs) {
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
    public void delete(Image entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Image> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Image> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Image> findAll(Pageable pageable) {
        return null;
    }
}
