package org.company.bookshop.api.dto.auth;

import lombok.Builder;

@Builder
public record AuthResponseDTO(
        String phone,
        String accessToken,
        String refreshToken
) {
}
