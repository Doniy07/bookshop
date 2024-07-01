package org.company.youtube.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreateDTO {

    @NotBlank(message = "Category name required")
    private String name;
}
