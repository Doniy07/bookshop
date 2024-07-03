package org.company.youtube.dto.video;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoUpdateDTO {

    private String id; // TO DELETE
    private String title;
    private String description;
    private Integer categoryId;
    private List<String> tagIds;

}
