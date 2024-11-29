package com.krince.memegle.domain.category.controller;

import com.krince.memegle.domain.category.dto.CategoryDto;
import com.krince.memegle.global.constant.Criteria;
import com.krince.memegle.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apis/client/categories")
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {

    @Override
    @GetMapping
    public ResponseEntity<SuccessResponse<List<CategoryDto>>> getCategories(@RequestParam Criteria criteria) {
        return null;
    }
}
