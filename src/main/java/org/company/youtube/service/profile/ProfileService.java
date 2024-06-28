package org.company.youtube.service.profile;

import lombok.extern.slf4j.Slf4j;
import org.company.youtube.dto.attach.AttachDTO;
import org.company.youtube.dto.profile.ProfileCreateDTO;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.dto.profile.ProfileUpdateDTO;
import org.company.youtube.entity.profile.ProfileEntity;
import org.company.youtube.enums.ProfileStatus;
import org.company.youtube.exception.AppBadException;
import org.company.youtube.repository.profile.ProfileRepository;
import org.company.youtube.service.attach.AttachService;
import org.company.youtube.service.auth.AuthService;
import org.company.youtube.util.MD5Util;
import org.company.youtube.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AttachService attachService;

    public ProfileService(ProfileRepository profileRepository, AuthService authService, AttachService attachService) {
        this.profileRepository = profileRepository;
        this.attachService = attachService;
    }


    public ProfileDTO create(ProfileCreateDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));
        entity.setStatus(dto.getStatus());
        entity.setRole(dto.getRole());

        profileRepository.save(entity);

        return toDTO(entity);
    }

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean changePassword(String oldPassword, String newPassword, String newPasswordRepeat) {

        if (!newPassword.equals(newPasswordRepeat)) {
            throw new AppBadException("Passwords do not match");
        }

        ProfileEntity entity = SecurityUtil.getProfile();
        Objects.requireNonNull(entity);

        if (!entity.getPassword().equals(MD5Util.getMD5(oldPassword))) {
            throw new AppBadException("Wrong password");
        }

        entity.setPassword(MD5Util.getMD5(newPassword));
        profileRepository.save(entity);
        return true;
    }

    public Boolean updateEmail(String newEmail) {
        ProfileEntity entity = SecurityUtil.getProfile();
        Objects.requireNonNull(entity);
//        sendRegistrationEmail(entity.getId(), newEmail);

        entity.setTempEmail(newEmail);
        profileRepository.save(entity);
        return true;
    }

    public ProfileDTO update(ProfileUpdateDTO profileUpdateDTO) {
        ProfileEntity entity = SecurityUtil.getProfile();
        Objects.requireNonNull(entity);
        entity.setName(profileUpdateDTO.getName());
        entity.setSurname(profileUpdateDTO.getSurname());
        profileRepository.save(entity);
        return toDTO(entity);
    }

    public ProfileDTO updateAttach(MultipartFile file) {
        ProfileEntity entity = SecurityUtil.getProfile();
        Objects.requireNonNull(entity);

        attachService.deleteAttach(entity.getPhotoId());
        AttachDTO attachDTO = attachService.saveAttach(file);
        if (attachDTO == null) {
            throw new AppBadException("Attach not found");
        }

        entity.setPhotoId(attachDTO.getId());
        profileRepository.save(entity);
        return toDTO(entity);
    }

/*    public ProfileEntity getProfile(String id) {
        log.error("Profile not found id = {}", id);
        return profileRepository.findById(id).orElseThrow(() -> new AppBadException("Profile not found"));
    }*/
}
