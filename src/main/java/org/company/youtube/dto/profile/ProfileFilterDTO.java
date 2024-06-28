package org.company.youtube.dto.profile;

import lombok.Getter;
import lombok.Setter;
import org.company.youtube.enums.ProfileRole;


import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileFilterDTO {
    private String name;
    private String surname;
    private String phone;
    private ProfileRole role;
    private LocalDateTime createdDateTo;
    private LocalDateTime createdDateFrom;
}
