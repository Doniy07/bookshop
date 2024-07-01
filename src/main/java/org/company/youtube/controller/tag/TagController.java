package org.company.youtube.controller.tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.tag.TagCreateDTO;
import org.company.youtube.dto.tag.TagDTO;
import org.company.youtube.service.tag.TagService;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

//        1. Create Category (ADMIN)

    @PostMapping("/adm/create")
    public ResponseEntity<TagDTO> addCategory(@Valid @RequestBody TagCreateDTO dto) {
        return ResponseEntity.ok().body(tagService.create(dto));
    }

//        2. Update Category (ADMIN)

    @PutMapping("/adm/update/{id}")
    public ResponseEntity<Boolean> updateCategory(@PathVariable("id") Integer id,
                                                  @Valid @RequestBody TagCreateDTO dto) {
        return ResponseEntity.ok().body(tagService.update(id, dto));
    }

//        3. Delete Category (ADMIN)

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(tagService.delete(id));
    }

//        4. Category List Pagination (ADMIN)

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<TagDTO>> pageable(@RequestParam(value = "page", defaultValue = "1") int page,
                                                          @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<TagDTO> tagList = tagService.pagination(page - 1, size);
        return ResponseEntity.ok().body(tagList);
    }
}
