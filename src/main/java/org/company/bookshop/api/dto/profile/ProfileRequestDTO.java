package org.company.bookshop.api.dto.profile;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.company.bookshop.api.enums.ProfileRole;
import org.company.bookshop.api.enums.ProfileStatus;

@Builder
public record ProfileRequestDTO(
        @NotBlank
        String fio,
        @NotBlank
        String phone,
        @NotBlank
        String password,
        ProfileStatus status,
        ProfileRole role
) {
}