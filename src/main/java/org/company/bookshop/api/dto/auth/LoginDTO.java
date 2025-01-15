package org.company.bookshop.api.dto.auth;

import lombok.Builder;

@Builder
public record LoginDTO(
        String phone,
        String password
) {}
