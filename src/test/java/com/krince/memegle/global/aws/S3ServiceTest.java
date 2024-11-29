package com.krince.memegle.global.aws;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tags({
        @Tag("test"),
        @Tag("unitTest"),
        @Tag("mockTest")
})
@DisplayName("s3 서비스 테스트(S3Service)")
@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

    @Configuration
    static class TestConfig {
        @Bean
        public AmazonS3 amazonS3() {
            return mock(AmazonS3.class);
        }

        @Bean
        public S3ServiceImpl s3Service(
                AmazonS3 amazonS3,
                @Value("${cloud.aws.s3.bucket}") String bucket,
                @Value("${cloud.aws.region.static}") String region
        ) {
            return new S3ServiceImpl(amazonS3, bucket, region);
        }
    }

    @InjectMocks
    S3ServiceImpl s3Service;

    @Mock
    AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    String bucket;

    @Value("${cloud.aws.region.static}")
    String region;

    @Test
    @DisplayName("이미지 업로드 테스트")
    void uploadImage() throws Exception {
        //given
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getOriginalFilename()).thenReturn("testFile.jpeg");
        when(mockMultipartFile.getContentType()).thenReturn("image/jpeg");
        when(mockMultipartFile.getSize()).thenReturn(123L);

        //when
        String fileUrl = s3Service.uploadFile(mockMultipartFile);

        //then
        verify(amazonS3, times(1)).putObject(any());
        assertThat(fileUrl).startsWith("https://" + bucket + ".s3." + region + ".amazonaws.com/");
    }
}