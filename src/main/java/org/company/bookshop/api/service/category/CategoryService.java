package org.company.bookshop.api.service.category;



import org.company.bookshop.api.dto.category.CategoryRequestDTO;
import org.company.bookshop.api.dto.category.CategoryRequestUpdateDTO;
import org.company.bookshop.api.dto.category.CategoryResponseDTO;
import org.company.bookshop.api.util.ApiResponse;

import java.util.List;

public interface CategoryService {

    ApiResponse<List<CategoryResponseDTO>> findAll();

    ApiResponse<CategoryResponseDTO> findById(String genreId);

    ApiResponse<CategoryResponseDTO> save(CategoryRequestDTO request);

    ApiResponse<CategoryResponseDTO> update(CategoryRequestUpdateDTO request);

    void deleteById(String genreId);
}