package org.company.bookshop.api.service.profile;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.company.bookshop.api.dto.profile.ProfileRequestDTO;
import org.company.bookshop.api.dto.profile.ProfileRequestUpdateBalanceDTO;
import org.company.bookshop.api.dto.profile.ProfileRequestUpdateDTO;
import org.company.bookshop.api.dto.profile.ProfileResponseDTO;
import org.company.bookshop.api.entity.ProfileEntity;
import org.company.bookshop.api.exception.BadRequestException;
import org.company.bookshop.api.repository.ProfileRepository;
import org.company.bookshop.api.util.ApiResponse;
import org.company.bookshop.api.util.MD5Util;
import org.company.bookshop.api.util.PhoneUtil;
import org.company.bookshop.api.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProfileServiceImpl implements ProfileService {

    ProfileRepository profileRepository;

    @Override
    public ApiResponse<List<ProfileResponseDTO>> findAll() {
        return ApiResponse.ok(profileRepository.findAllByVisibleTrue()
                .stream().map(mapToResponse()).toList());
    }

    @Override
    public ApiResponse<ProfileResponseDTO> findById(String profileId) {
        ProfileEntity profile = getById(profileId);
        return ApiResponse.ok(mapToResponse().apply(profile));
    }

    @Override
    public ApiResponse<ProfileResponseDTO> save(ProfileRequestDTO request) {
        checkPhone(request.phone());
        profileRepository.save(mapToEntity().apply(request));
        return ApiResponse.ok();
    }


    private Function<ProfileRequestDTO, ProfileEntity> mapToEntity() {
        return request -> ProfileEntity.builder()
                .fio(request.fio())
                .phone(request.phone())
                .password(MD5Util.getMD5(request.password()))
                .balance(0)
                .status(request.status())
                .role(request.role())
                .build();
    }

    private Function<ProfileEntity, ProfileResponseDTO> mapToResponse() {
        return entity -> ProfileResponseDTO.builder()
                .fio(entity.getFio())
                .phone(entity.getPhone())
                .role(entity.getRole().name())
                .status(entity.getStatus().name())
                .build();
    }


    @Override
    public ApiResponse<ProfileResponseDTO> update(String profileId, ProfileRequestDTO request) {
        if (request.phone() != null) checkPhone(request.phone());
        ProfileEntity profile = getById(profileId);
        profile.setFio(request.fio());
        profile.setPhone(request.phone());
        profile.setPassword(MD5Util.getMD5(request.password()));
        profile.setStatus(request.status());
        profile.setRole(request.role());
        profileRepository.save(profile);
        log.info("Profile updated successfully with id: {}", profileId);
        return ApiResponse.ok("Profile updated successfully", mapToResponse().apply(profile));
    }

    @Override
    public ApiResponse<ProfileResponseDTO> update(ProfileRequestUpdateDTO request) {
        if (request.phone() != null) checkPhone(request.phone());
        ProfileEntity profile = getById(SecurityUtil.getProfileId());
        profile.setFio(request.fio());
        profile.setPhone(request.phone());
        profile.setPassword(MD5Util.getMD5(request.password()));
        profileRepository.save(profile);
        return null;
    }

    @Override
    public void updateBalance(String profileId, ProfileRequestUpdateBalanceDTO request) {
        ProfileEntity profile = getById(profileId);
        profile.setBalance(profile.getBalance() + request.balance());
        profileRepository.save(profile);
    }

    public void updateBalance(String profileId, Double balance) {
        profileRepository.updateBalance(profileId, balance);
    }

    @Override
    public void delete(String profileId) {
        log.info("Profile deleted successfully with id: {}", profileId);
        profileRepository.deleteProfileById(getById(profileId).getId());
    }

    @Override
    public ApiResponse<Double> viewBalance() {
        return ApiResponse.ok(getById(SecurityUtil.getProfileId()).getBalance());
    }

    public ProfileEntity getById(String profileId) {
        return profileRepository.findByIdAndVisibleTrue(profileId).orElseThrow(() ->
                new BadRequestException(String.format("Librarian with id %s not found", profileId))
        );
    }

    private void checkPhone(String phone) {
        PhoneUtil.isCorrectPhone(phone);
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(phone);
        if (optional.isPresent()) {
            log.error("Phone {} already exists", phone);
            throw new BadRequestException("This phone number already exists");
        }
    }

}
