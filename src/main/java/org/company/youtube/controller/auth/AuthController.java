package org.company.youtube.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.company.youtube.dto.auth.LoginDTO;
import org.company.youtube.dto.auth.RegistrationDTO;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.service.auth.AuthService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller", description = "Api list for Auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //    	1. Registration (with email verification)
//	     id,name,surname,email,main_photo (url)
    @Operation(summary = "Registration", description = "Api for auth registration")
    @PostMapping("/registration")
    public ResponseEntity<String> registrationByEmail(@Valid @RequestBody RegistrationDTO dto) {
        log.info("Registration name = {}  email = {} ", dto.getName(), dto.getEmail());
        String response = authService.registration(dto);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Verification", description = "Api for auth Verification")
    @GetMapping("/verification/{userId}")
    public ResponseEntity<String> verificationByEmail(@PathVariable("userId") String userId) {
        String response = authService.verification(userId);
        return ResponseEntity.ok().body(response);
    }

    // resend by email
    @Operation(summary = "Registration Resend", description = "Api for auth Registration Resend")
    @GetMapping("/registration/resend/{email}")
    public ResponseEntity<String> registrationResend(@PathVariable("email") String email) {
        String body = authService.registrationResend(email);
        return ResponseEntity.ok().body(body);
    }

    //	    2. Authorization
    @Operation(summary = "Login", description = "Api for auth Login")
    @PostMapping("/login")
    public HttpEntity<ProfileDTO> loginUser(@RequestBody LoginDTO loginDto) {
        ProfileDTO result = authService.login(loginDto);
        return ResponseEntity.ok().body(result);
    }

}
