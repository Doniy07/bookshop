package org.company.youtube.dto.category;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryDTO {

    private Integer id;
    private String name;
    private Boolean visible;
    private LocalDateTime createdDate;
}
