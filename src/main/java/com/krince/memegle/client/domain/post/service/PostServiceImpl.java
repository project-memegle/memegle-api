package com.krince.memegle.client.domain.post.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.krince.memegle.client.domain.image.entity.Image;
import com.krince.memegle.client.domain.image.service.ImageService;
import com.krince.memegle.client.domain.post.dto.response.ResponsePostListDto;
import com.krince.memegle.client.domain.post.entity.Post;
import com.krince.memegle.client.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final AmazonS3Client amazonS3Client;
    private final PostRepository postRepository;
    private final ImageService imageService;

    @Override
    @Transactional
    public void resistPost(MultipartFile mimeImage, String content) {
        final String BUCKET = "mimegle";
        try {
            String fileName = mimeImage.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(mimeImage.getContentType());
            metadata.setContentLength(mimeImage.getSize());

            amazonS3Client.putObject(BUCKET, fileName, mimeImage.getInputStream(), metadata);
            String mimeImageUrl = amazonS3Client.getUrl(BUCKET, fileName).toString();
            Post post = Post.builder().content(content).build();
            Image image = imageService.createMimeImage(mimeImageUrl, post);
            post.saveImage(image);

            postRepository.save(post);
            imageService.saveMimeImage(image);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public List<ResponsePostListDto> getPosts() {
        boolean isConfirm = true;

        List<Post> findPosts = postRepository.findAllByIsConfirm(isConfirm);
        List<ResponsePostListDto> responsePostListDtos = findPosts.stream().map(post -> {
            return ResponsePostListDto.builder()
                    .postId(post.getId())
                    .postImageUrl(post.getImages().get(0).getImageUrl())
                    .createdAt(post.getCreatedAt())
                    .build();
        }).toList();

        return responsePostListDtos;
    }
}
