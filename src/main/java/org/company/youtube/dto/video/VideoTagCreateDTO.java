package org.company.youtube.dto.video;

import lombok.Data;

import java.util.List;

@Data
public class VideoTagCreateDTO {
    private List<String> tags;
}
