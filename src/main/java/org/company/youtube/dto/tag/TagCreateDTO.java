package org.company.youtube.dto.tag;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagCreateDTO {

    @NotBlank(message = "Tag name required")
    private String name;
}
