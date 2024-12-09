package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.RegistImageDto;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.repository.ImageRepository;
import com.krince.memegle.domain.tag.entity.Tag;
import com.krince.memegle.domain.tag.service.TagService;
import com.krince.memegle.global.aws.S3Service;
import com.krince.memegle.global.constant.ImageCategory;
import com.krince.memegle.global.dto.PageableDto;
import com.krince.memegle.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final S3Service s3Service;
    private final TagService tagService;

    @Override
    public ViewImageDto getImage(Long imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(NoSuchElementException::new);

        return ViewImageDto.of(image);
    }

    @Override
    public List<ViewImageDto> getCategoryImages(ImageCategory imageCategory, PageableDto pageableDto) {
        Pageable pageable = PageUtil.createSortedPageable(pageableDto);
        Page<Image> images = imageRepository.findAllByImageCategory(imageCategory, pageable);

        return images.stream()
                .map(ViewImageDto::of)
                .toList();
    }

    @Override
    public String registMemeImage(RegistImageDto registImageDto) throws IOException {
        List<Tag> tagList = tagService.getTags(registImageDto.getTags(), registImageDto.getDelimiter());
        String memeImageUrl = s3Service.uploadFile(registImageDto.getMemeImageFile());
        Image image = Image.of(memeImageUrl, registImageDto.getImageCategory());
        Image savedImage = imageRepository.save(image);
        tagList.forEach(tag -> tagService.registTagMap(savedImage, tag));

        return memeImageUrl;
    }
}
