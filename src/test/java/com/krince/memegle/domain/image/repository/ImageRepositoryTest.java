package com.krince.memegle.domain.image.repository;

import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.entity.RegistStatus;
import com.krince.memegle.global.constant.ImageCategory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Tags({
        @Tag("test"),
        @Tag("integrationTest")
})
@ActiveProfiles("test")
@SpringBootTest
@DisplayName("이미지 리포지토리 테스트(ImageRepository)")
class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @Nested
    @DisplayName("카테고리 이미지 리스트 조회")
    class FindAllByImageCategory {

        @Nested
        @DisplayName("성공")
        class FindAllByImageCategorySuccess {

            @Test
            @DisplayName("카테고리 이미지 리스트 조회시 해당 카테고리가 아닌 이미지는 조회되지 않는다.")
            void findAllByImageCategoryOrderByCreatedAtDesc() {
                //given
                ImageCategory imageCategory = ImageCategory.MUDO;
                int imageCount = 5;
                Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());

                saveImages(imageCount, imageCategory);
                saveImages(1, ImageCategory.ETC);

                //when
                Page<Image> images = imageRepository.findAllByImageCategory(imageCategory, pageable);

                //then
                assertThat(images.getTotalElements()).isEqualTo(imageCount);
            }
        }
    }

    private void saveImages(int imageCount, ImageCategory imageCategory) {
        for (int i = 1; i <= imageCount; i++) {
            imageRepository.save(
                    Image.builder()
                            .imageUrl("https://www.testImage" + i + UUID.randomUUID() + ".com")
                            .registStatus(RegistStatus.REGIST)
                            .imageCategory(imageCategory)
                            .build()
            );
        }
    }
}