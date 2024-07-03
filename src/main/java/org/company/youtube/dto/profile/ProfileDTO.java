package org.company.youtube.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.company.youtube.dto.attach.AttachDTO;
import org.company.youtube.enums.ProfileRole;
import org.company.youtube.enums.ProfileStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private ProfileStatus status;
    private ProfileRole role;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String photoId;
    private AttachDTO photo;
    private String jwt;
}
