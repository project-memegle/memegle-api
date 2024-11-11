package com.krince.memegle.domain.image.repository;

import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.global.constant.ImageCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Page<Image> findAllByImageCategory(ImageCategory imageCategory, Pageable pageable);
}
