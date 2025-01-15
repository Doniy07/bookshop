package org.company.bookshop.api.dto.profile;


public record ProfileRequestUpdateDTO(
        String fio,
        String phone,
        String password
) {
}
