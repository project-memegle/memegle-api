package com.krince.memegle.global.aws.fake;

import com.amazonaws.services.s3.AmazonS3;
import com.krince.memegle.global.aws.S3Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FakeS3Service implements S3Service {

    private final AmazonS3 amazonS3;
    private final String bucket;
    private final String region;

    public FakeS3Service(AmazonS3 amazonS3, String bucket, String region) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
        this.region = region;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return "https://testImage.com";
    }
}
