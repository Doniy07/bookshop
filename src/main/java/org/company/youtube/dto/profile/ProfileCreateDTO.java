package org.company.youtube.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.company.youtube.enums.ProfileRole;
import org.company.youtube.enums.ProfileStatus;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileCreateDTO {
    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "Surname required")
    private String surname;
    @NotBlank(message = "Email required")
    private String email;
    @NotBlank(message = "Phone required")
    private String phone;
    @NotBlank(message = "Password required")
    private String password;
    @Enumerated
    private ProfileStatus status;
    @Enumerated
    private ProfileRole role;
}
