package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.RegistImageDto;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.repository.fake.FakeImageRepository;
import com.krince.memegle.domain.image.repository.ImageRepository;
import com.krince.memegle.domain.tag.repository.fake.FakeTagMapRepository;
import com.krince.memegle.domain.tag.repository.fake.FakeTagRepository;
import com.krince.memegle.domain.tag.repository.TagMapRepository;
import com.krince.memegle.domain.tag.repository.TagRepository;
import com.krince.memegle.domain.tag.service.fake.FakeTagService;
import com.krince.memegle.domain.tag.service.TagService;
import com.krince.memegle.global.aws.fake.FakeAmazonS3;
import com.krince.memegle.global.aws.fake.FakeS3Service;
import com.krince.memegle.global.aws.S3Service;
import com.krince.memegle.global.constant.Criteria;
import com.krince.memegle.global.constant.ImageCategory;
import com.krince.memegle.global.dto.PageableDto;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Tags({
        @Tag("test"),
        @Tag("unitTest")
})
@DisplayName("이미지 서비스 테스트(ImageService)")
class ImageServiceTest {

    static ImageService imageService;

    static ImageRepository imageRepository;

    static S3Service s3Service;

    static TagService tagService;

    static TagRepository tagRepository;

    static TagMapRepository tagMapRepository;

    @BeforeAll
    static void setUp() {
        imageRepository = new FakeImageRepository();
        tagRepository = new FakeTagRepository();
        tagMapRepository = new FakeTagMapRepository();
        tagService = new FakeTagService(tagRepository, tagMapRepository);
        s3Service = new FakeS3Service(new FakeAmazonS3(), "testBucket", "testRegion");
        imageService = new ImageServiceImpl(imageRepository, s3Service, tagService);
    }

    @AfterEach
    void tearDown() {
        imageRepository.deleteAll();
        tagRepository.deleteAll();
        tagMapRepository.deleteAll();
    }

    @Nested
    @DisplayName("이미지 단건 조회")
    class GetImage {

        @Nested
        @DisplayName("성공")
        class GetImageSuccess {

            @Test
            @DisplayName("success")
            void getImageSuccess() {
                //given
                Image image = Image.builder().build();
                imageRepository.save(image);

                //when
                ViewImageDto viewImageDto = imageService.getImage(1L);

                //then
                assertThat(viewImageDto.getId()).isEqualTo(1L);
            }
        }

        @Nested
        @DisplayName("실패")
        class GetImageFail {

            @Test
            @DisplayName("없는 이미지를 조회하려 하면 예외를 반환한다.")
            void getImageNotFindResource() {
                //given
                Image image = Image.builder().build();
                imageRepository.save(image);

                //when
                Exception exception = assertThrows(Exception.class, () -> imageService.getImage(100L));

                //then
                assertThat(exception).isInstanceOf(NoSuchElementException.class);
            }
        }
    }

    @Nested
    @DisplayName("카테고리 이미지 리스트 조회")
    class GetCategoryImages {

        @Nested
        @DisplayName("성공")
        class GetCategoryImagesSuccess {

            @Test
            @DisplayName("success")
            void getCategoryImages() {
                //given
                PageableDto pageableDto = PageableDto.builder()
                        .criteria(Criteria.CREATED_AT)
                        .page(1)
                        .size(1)
                        .build();

                //when
                List<ViewImageDto> images = imageService.getCategoryImages(ImageCategory.MUDO, pageableDto);

                //then
                assertThat(images.size()).isEqualTo(0);
            }
        }
    }

    @Nested
    @DisplayName("밈 이미지 등록 신청 테스트")
    class RegistMemeImage {

        @Nested
        @DisplayName("성공")
        class RegistMemeImageSuccess {

            @Test
            @DisplayName("success")
            void registMemeImage() throws IOException {
                //given
                RegistImageDto registImageDto = RegistImageDto.builder().build();

                //when
                String imageURL = imageService.registMemeImage(registImageDto);

                //then
                assertThat(imageURL).isEqualTo("https://testImage.com");
            }
        }
    }
}