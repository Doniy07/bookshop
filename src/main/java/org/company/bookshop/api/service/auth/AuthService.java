package org.company.bookshop.api.service.auth;

import org.company.bookshop.api.dto.auth.*;
import org.company.bookshop.api.util.ApiResponse;

public interface AuthService {

    ApiResponse<String> registration(RegistrationDTO registrationDTO);

    ApiResponse<AuthResponseDTO> login(LoginDTO loginDTO);

    ApiResponse<RefreshResponseDTO> getAccessToken(RefreshDTO refreshDTO);
}
