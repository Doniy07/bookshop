package org.company.youtube.dto.email;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailDTO {
    private String id;
    private String toEmail;
    private String title;
    private String message;
    private Boolean visible;
    private LocalDateTime createdDate;
}
