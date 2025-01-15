package org.company.bookshop.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.bookshop.api.dto.auth.*;
import org.company.bookshop.api.service.auth.AuthService;
import org.company.bookshop.api.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<ApiResponse<String>> registerWithEmail(@Valid @RequestBody RegistrationDTO registrationDTO) {
        log.info("Registration user with phone : {}", registrationDTO.phone());
        return ResponseEntity.ok(authService.registration(registrationDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(
            @Valid @RequestBody LoginDTO loginDTO) {
        log.info("login user: {}", loginDTO);
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @PostMapping("/get-access-token")
    public ResponseEntity<ApiResponse<RefreshResponseDTO>> getAccessToken(
            @RequestBody RefreshDTO refreshDTO) {
        return ResponseEntity.ok().body(authService.getAccessToken(refreshDTO));
    }
}
