package org.company.bookshop.api.dto.auth;

import lombok.Builder;

@Builder
public record RefreshResponseDTO(
        String phone,
        String accessToken
) {}
