package com.krince.memegle.global.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;
    private final String bucket;
    private final String region;

    public S3ServiceImpl(
            AmazonS3 amazonS3,
            @Value("${cloud.aws.s3.bucket}") String bucket,
            @Value("${cloud.aws.region.static}") String region) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
        this.region = region;
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String uniqueFileName = generateUniqueFileName(fileExtension);
        InputStream inputStream = multipartFile.getInputStream();
        setMetaData(multipartFile, uniqueFileName, inputStream);

        return generateFileUrl(uniqueFileName);
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private String generateUniqueFileName(String fileExtension) {
        String uuid = UUID.randomUUID().toString();
        return uuid + fileExtension;
    }

    private void setMetaData(MultipartFile multipartFile, String uniqueFileName, InputStream inputStream) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        amazonS3.putObject(new PutObjectRequest(bucket, uniqueFileName, inputStream, metadata));
    }

    private String generateFileUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucket, region, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
}
