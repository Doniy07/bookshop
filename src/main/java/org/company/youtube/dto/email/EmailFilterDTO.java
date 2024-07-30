package org.company.youtube.dto.email;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmailFilterDTO {

    private String email;
    private LocalDateTime to;
    private LocalDateTime from;
}
