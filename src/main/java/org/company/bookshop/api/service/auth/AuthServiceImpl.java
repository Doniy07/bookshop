package org.company.bookshop.api.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.bookshop.api.dto.auth.*;
import org.company.bookshop.api.entity.ProfileEntity;
import org.company.bookshop.api.enums.ProfileRole;
import org.company.bookshop.api.enums.ProfileStatus;
import org.company.bookshop.api.exception.BadRequestException;
import org.company.bookshop.api.repository.ProfileRepository;
import org.company.bookshop.api.util.ApiResponse;
import org.company.bookshop.api.util.JWTUtil;
import org.company.bookshop.api.util.MD5Util;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ProfileRepository profileRepository;
    private final JWTUtil jwtUtil;

    @Override
    public ApiResponse<String> registration(RegistrationDTO registrationDTO) {
        checkPhone(registrationDTO.phone());
        ProfileEntity entity = ProfileEntity.builder()
                .fio(registrationDTO.fio())
                .phone(registrationDTO.phone())
                .password(MD5Util.getMD5(registrationDTO.password()))
                .status(ProfileStatus.ACTIVE)
                .role(ProfileRole.ROLE_CUSTOMER)
                .build();
        profileRepository.save(entity);
        return ApiResponse.ok("Registration successful", entity.getPhone());
    }

    private void checkPhone(String phone) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(phone);
        if (optional.isPresent()) {
            log.error("Phone {} already exists", phone);
            throw new BadRequestException("This phone number already exists");
        }
    }

    @Override
    public ApiResponse<AuthResponseDTO> login(LoginDTO loginDTO) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(loginDTO.phone());
        if (optional.isEmpty()) {
            log.error("Phone {} not found", loginDTO.phone());
            return ApiResponse.unAuthorized("Phone unauthorized");
        }
        ProfileEntity profile = optional.get();

        if (!MD5Util.getMD5(loginDTO.password()).equals(profile.getPassword()))
            throw new BadRequestException("Invalid password");

        if (!profile.getStatus().equals(ProfileStatus.ACTIVE))
            throw new BadRequestException("Your account is not active");

        AuthResponseDTO response = AuthResponseDTO.builder()
                .phone(profile.getPhone())
                .accessToken(jwtUtil.getAccessToken(profile.getPhone()))
                .refreshToken(jwtUtil.getRefreshToken(profile.getPhone()))
                .build();
        return ApiResponse.ok("Login successful", response);
    }

    @Override
    public ApiResponse<RefreshResponseDTO> getAccessToken(RefreshDTO refreshDTO) {
        String phone = jwtUtil.decode(refreshDTO.refreshToken());
        ProfileEntity profile = findProfileByPhone(phone);
        RefreshResponseDTO response = RefreshResponseDTO.builder()
                .phone(profile.getPhone())
                .accessToken(jwtUtil.getAccessToken(profile.getPhone()))
                .build();
        return ApiResponse.ok("Get access token successful", response);
    }

    private ProfileEntity findProfileByPhone(String phone) {
        return profileRepository.findByPhoneAndVisibleTrue(phone)
                .orElseThrow(() -> new BadRequestException("Profile not found"));
    }
}
