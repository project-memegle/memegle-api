package com.krince.memegle.admin.domain.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.krince.memegle.admin.domain.image.dto.request.RequestConfirmMimeImageDto;
import com.krince.memegle.admin.domain.image.repository.AdminImageRepository;
import com.krince.memegle.admin.domain.post.service.PostService;
import com.krince.memegle.client.domain.image.entity.Image;
import com.krince.memegle.client.domain.post.entity.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("adminImageService")
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final AdminImageRepository adminImageRepository;
    private final PostService postService;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    @Transactional
    public void confirmMimeImage(Long mimeImageId, RequestConfirmMimeImageDto requestConfirmMimeImageDto) {

        Boolean isConfirm = requestConfirmMimeImageDto.getIsConfirm();
        Image image = adminImageRepository.findById(mimeImageId).orElseThrow(IllegalArgumentException::new);
        Post post = image.getPost();

        if (isConfirm) {

            post.changeIsConfirm(isConfirm);
        }

        if (!isConfirm) {

            final String DELIMITER = "amazonaws.com/";
            String mimeImageUrl = image.getImageUrl();
            String[] splitArray = mimeImageUrl.split(DELIMITER);
            String mimeImageName = splitArray[splitArray.length - 1];

            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, mimeImageName));
            adminImageRepository.delete(image);
            postService.deletePost(post);
        }
    }
}
