package org.company.youtube.dto.email;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmailDTO {
    private String id;
    private String toEmail;
    private String title;
    private String message;
    private LocalDateTime createdDate;
}
