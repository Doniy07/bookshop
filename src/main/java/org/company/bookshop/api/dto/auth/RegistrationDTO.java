package org.company.bookshop.api.dto.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegistrationDTO(
        @NotBlank(message = "Fio required")
        String fio,
        @NotBlank(message = "Phone required")
        String phone,
        @NotBlank(message = "Password  required")
        String password
) {
}
