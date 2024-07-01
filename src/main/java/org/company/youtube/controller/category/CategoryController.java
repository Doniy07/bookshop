package org.company.youtube.controller.category;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.company.youtube.dto.category.CategoryCreateDTO;
import org.company.youtube.dto.category.CategoryDTO;
import org.company.youtube.service.category.CategoryService;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //        1. Create Category (ADMIN)

    @PostMapping("/adm/create")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryCreateDTO categoryDTO) {
        return ResponseEntity.ok().body(categoryService.create(categoryDTO));
    }

//        2. Update Category (ADMIN)

    @PutMapping("/adm/update/{id}")
    public ResponseEntity<Boolean> updateCategory(@PathVariable("id") Integer id,
                                                  @Valid @RequestBody CategoryCreateDTO dto) {
        return ResponseEntity.ok().body(categoryService.update(id, dto));
    }

//        3. Delete Category (ADMIN)

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(categoryService.delete(id));
    }

//       4. Category List Pagination (ADMIN)

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<CategoryDTO>> pageable(@RequestParam(value = "page", defaultValue = "1") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<CategoryDTO> categoryList = categoryService.pagination(page - 1, size);
        return ResponseEntity.ok().body(categoryList);
    }

}
