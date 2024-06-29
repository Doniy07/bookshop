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
import org.company.youtube.service.email.EmailService;
import org.company.youtube.service.email.MailSenderService;
import org.company.youtube.util.MD5Util;
import org.company.youtube.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AttachService attachService;
    private final MailSenderService mailSenderService;
    private final EmailService emailService;

    public ProfileService(ProfileRepository profileRepository, AttachService attachService, MailSenderService mailSenderService, EmailService emailService) {
        this.profileRepository = profileRepository;
        this.attachService = attachService;
        this.mailSenderService = mailSenderService;
        this.emailService = emailService;
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

    public String updateEmail(String newEmail) {
        ProfileEntity entity = SecurityUtil.getProfile();
        Objects.requireNonNull(entity);

        if (!entity.getEmail().equals(newEmail)) {
            sendRegistrationEmail(entity.getId(), newEmail);
            entity.setTempEmail(newEmail);
            entity.setStatus(ProfileStatus.REGISTRATION);
            entity.setUpdatedDate(LocalDateTime.now());
            profileRepository.save(entity);
            return "To complete your registration, please verify your email";
        }
        return "Email don't changed";
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

    public void sendRegistrationEmail(String profileId, String email) {
        // send email
        String url = "http://localhost:8080/profile/verification/" + profileId;
        String formatText = "<style>\n" +
                "    a:link, a:visited {\n" +
                "        background-color: #f44336;\n" +
                "        color: white;\n" +
                "        padding: 14px 25px;\n" +
                "        text-align: center;\n" +
                "        text-decoration: none;\n" +
                "        display: inline-block;\n" +
                "    }\n" +
                "\n" +
                "    a:hover, a:active {\n" +
                "        background-color: red;\n" +
                "    }\n" +
                "</style>\n" +
                "<div style=\"text-align: center\">\n" +
                "    <h1>Welcome to kun.uz web portal</h1>\n" +
                "    <br>\n" +
                "    <p>Please button lick below to complete registration</p>\n" +
                "    <div style=\"text-align: center\">\n" +
                "        <a href=\"%s\" target=\"_blank\">This is a link</a>\n" +
                "    </div>";
        String text = String.format(formatText, url);
        String title = "Complete registration";
        mailSenderService.send(email, title, text);
        emailService.create(email,title, text); // create history
    }

    public String verification(String userId) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }

        ProfileEntity entity = optional.get();
        emailService.isNotExpiredEmail(entity.getTempEmail());// check for expireation date

        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }

        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        profileRepository.updateEmail(userId, entity.getTempEmail());
        return "Success";
    }

    public String resend(String email) {
        Optional<ProfileEntity> optional = profileRepository.findByTempEmail(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email not exists");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }
        emailService.checkEmailLimit(email);
        sendRegistrationEmail(entity.getId(), email);
        return "To complete your registration please verify your email.";
    }
}
