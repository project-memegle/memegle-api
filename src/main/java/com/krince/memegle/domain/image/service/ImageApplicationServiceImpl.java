package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.RegistImageDto;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.tag.entity.Tag;
import com.krince.memegle.domain.tag.entity.TagMap;
import com.krince.memegle.domain.tag.service.TagDomainService;
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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageApplicationServiceImpl implements ImageApplicationService {

    private final ImageDomainService imageDomainService;
    private final TagDomainService tagDomainService;
    private final S3Service s3Service;

    @Override
    public ViewImageDto getImage(Long imageId) {
        Image findImage = imageDomainService.getImageFromId(imageId);

        return ViewImageDto.of(findImage);
    }

    @Override
    public List<ViewImageDto> getCategoryImages(ImageCategory imageCategory, PageableDto pageableDto) {
        Pageable pageable = PageUtil.createSortedPageable(pageableDto);
        Page<Image> images = imageDomainService.getPageableImagesFromImageCategory(imageCategory, pageable);

        return images.stream()
                .map(ViewImageDto::of)
                .toList();
    }

    @Override
    @Transactional
    public String registMemeImage(RegistImageDto registImageDto) throws IOException {
        String tags = registImageDto.getTags();
        String delimiter = registImageDto.getDelimiter();
        String[] tagNameList = tags.split(delimiter);
        List<Tag> tagList = tagDomainService.getTagListFromTagNameList(tagNameList);

        String memeImageUrl = s3Service.uploadFile(registImageDto.getMemeImageFile());
        Image image = Image.of(memeImageUrl, registImageDto.getImageCategory());
        Image savedImage = imageDomainService.registImage(image);

        List<TagMap> tagMapList = tagList.stream().map(tag -> TagMap.of(tag, savedImage)).toList();
        tagDomainService.registTagMapList(tagMapList);

        return memeImageUrl;
    }
}
