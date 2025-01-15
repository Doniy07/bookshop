package org.company.bookshop.api.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.company.bookshop.api.dto.profile.ProfileRequestDTO;
import org.company.bookshop.api.dto.profile.ProfileRequestUpdateBalanceDTO;
import org.company.bookshop.api.dto.profile.ProfileRequestUpdateDTO;
import org.company.bookshop.api.dto.profile.ProfileResponseDTO;
import org.company.bookshop.api.service.profile.ProfileService;
import org.company.bookshop.api.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProfileController {

    ProfileService profileService;

    public static final String FETCH_ALL = "/all";
    public static final String FETCH_BY_ID = "/{id}";
    public static final String CREATE = "/create";
    public static final String UPDATE = "/update/{id}";
    public static final String UPDATE_BALANCE = "/update-balance/{id}";
    public static final String VIEW_BALANCE = "/view-balance";
    public static final String UPDATE_USER = "/update-user";
    public static final String DELETE = "/delete/{id}";

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(FETCH_ALL)
    public ResponseEntity<ApiResponse<List<ProfileResponseDTO>>> getAll() {
        return ResponseEntity.ok().body(profileService.findAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(FETCH_BY_ID)
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> getById(
            @PathVariable("id") String profileId) {
        return ResponseEntity.ok(profileService.findById(profileId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(CREATE)
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> create(
            @Valid @RequestBody ProfileRequestDTO request) {
        return ResponseEntity.ok(profileService.save(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(UPDATE)
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> update(
            @PathVariable("id") String profileId,
            @RequestBody ProfileRequestDTO request) {
        return ResponseEntity.ok(profileService.update(profileId, request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(UPDATE_BALANCE)
    public ResponseEntity<HttpStatus> updateBalance(
            @PathVariable("id") String profileId,
            @RequestBody ProfileRequestUpdateBalanceDTO request) {
        profileService.updateBalance(profileId, request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(VIEW_BALANCE)
    public ResponseEntity<ApiResponse<Double>> viewBalance() {
        return ResponseEntity.ok().body(profileService.viewBalance());
    }

    @PutMapping(UPDATE_USER)
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> updateUser(
            @RequestBody ProfileRequestUpdateDTO request) {
        return ResponseEntity.ok(profileService.update(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(DELETE)
    public ResponseEntity<HttpStatus> delete(
            @PathVariable("id") String profileId) {
        profileService.delete(profileId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
