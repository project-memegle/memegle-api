package com.krince.memegle.util;

import com.krince.memegle.global.dto.PageableDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {

    public static Pageable createSortedPageable(PageableDto pageableDto) {
        String criteria = pageableDto.getCriteria().getCriteria();
        Sort sort = Sort.by(criteria).descending();
        int pageNumber = pageableDto.getPage() - 1;
        int pageSize = pageableDto.getSize();

        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
