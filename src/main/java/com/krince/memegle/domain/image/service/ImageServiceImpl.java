package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.repository.ImageRepository;
import com.krince.memegle.global.ImageCategory;
import com.krince.memegle.global.dto.PageableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public ViewImageDto getImage(Long imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(NoSuchElementException::new);

        return generateViewImageDto(image);
    }

    @Override
    public List<ViewImageDto> getCategoryImages(ImageCategory imageCategory, PageableDto pageableDto) {
        Pageable pageable = createSortedPageable(pageableDto);
        Page<Image> images = imageRepository.findAllByImageCategory(imageCategory, pageable);

        return images.stream()
                .map(this::generateViewImageDto)
                .toList();
    }

    private Pageable createSortedPageable(PageableDto pageableDto) {
        String criteria = pageableDto.getCriteria().getCriteria();
        Sort sort = Sort.by(criteria).descending();
        int pageNumber = pageableDto.getPage() - 1;
        int pageSize = pageableDto.getSize();


        return PageRequest.of(pageNumber, pageSize, sort);
    }

    private ViewImageDto generateViewImageDto(Image image) {
        return ViewImageDto.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .imageCategory(image.getImageCategory())
                .createdAt(image.getCreatedAt())
                .modifiedAt(image.getModifiedAt())
                .build();
    }
}
