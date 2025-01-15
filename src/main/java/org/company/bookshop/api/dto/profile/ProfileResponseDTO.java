package org.company.bookshop.api.dto.profile;


import lombok.Builder;

@Builder
public record ProfileResponseDTO(
        String fio,
        String phone,
        String password,
        String status,
        String role
) {
}