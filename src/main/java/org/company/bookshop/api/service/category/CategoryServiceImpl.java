package org.company.bookshop.api.service.category;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.company.bookshop.api.dto.category.CategoryRequestDTO;
import org.company.bookshop.api.dto.category.CategoryRequestUpdateDTO;
import org.company.bookshop.api.dto.category.CategoryResponseDTO;
import org.company.bookshop.api.entity.CategoryEntity;
import org.company.bookshop.api.exception.BadRequestException;
import org.company.bookshop.api.repository.CategoryRepository;
import org.company.bookshop.api.util.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Override
    public ApiResponse<List<CategoryResponseDTO>> findAll() {
        return ApiResponse.ok(categoryRepository.findAllByVisibleTrue().stream().map(mapToResponse()).toList());
    }

    @Override
    public ApiResponse<CategoryResponseDTO> findById(String categoryId) {
        return ApiResponse.ok(mapToResponse().apply(getById(categoryId)));
    }

    @Override
    public ApiResponse<CategoryResponseDTO> save(CategoryRequestDTO request) {
        categoryRepository.findByTitle(request.title()).ifPresent(category -> {
            throw new BadRequestException("Category already exists");
        });
        categoryRepository.save(mapToEntity().apply(request));
        return ApiResponse.ok();
    }

    @Override
    public ApiResponse<CategoryResponseDTO> update(CategoryRequestUpdateDTO request) {
        getById(request.id());
        CategoryEntity entity = new CategoryEntity();
        entity.setId(request.id());
        entity.setTitle(request.title());
        categoryRepository.save(entity);
        return ApiResponse.ok();
    }

    @Override
    public void deleteById(String categoryId) {
        getById(categoryId);
        categoryRepository.deleteById(categoryId);
    }

    public CategoryEntity getById(String categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BadRequestException("Category not found"));

    }

    private Function<CategoryRequestDTO, CategoryEntity> mapToEntity() {
        return request -> CategoryEntity.builder()
                .title(request.title())
                .build();
    }


    private Function<CategoryEntity, CategoryResponseDTO> mapToResponse() {
        return entity -> CategoryResponseDTO.builder()
                .title(entity.getTitle())
                .build();
    }

}
