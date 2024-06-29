package org.company.youtube.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.company.youtube.dto.auth.LoginDTO;
import org.company.youtube.dto.auth.RegistrationDTO;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.entity.profile.ProfileEntity;
import org.company.youtube.enums.ProfileRole;
import org.company.youtube.enums.ProfileStatus;
import org.company.youtube.exception.AppBadException;
import org.company.youtube.repository.profile.ProfileRepository;
import org.company.youtube.service.attach.AttachService;
import org.company.youtube.service.email.EmailService;
import org.company.youtube.service.email.MailSenderService;
import org.company.youtube.util.JWTUtil;
import org.company.youtube.util.MD5Util;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {

    private final ProfileRepository profileRepository;
    private final MailSenderService mailSenderService;
    private final EmailService emailService;

    public AuthService(ProfileRepository profileRepository, MailSenderService mailSenderService, EmailService emailService) {
        this.profileRepository = profileRepository;
        this.mailSenderService = mailSenderService;
        this.emailService = emailService;
    }

    //    	1. Registration (with email verification)
//	         id,name,surname,email,main_photo (url)

    public String registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            log.warn("Email already exists email : {}", dto.getEmail());
            throw new AppBadException("Email already in use");

        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));
        entity.setPhotoId(dto.getPhotoId());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);

        profileRepository.save(entity);
        sendRegistrationEmail(entity.getId(), dto.getEmail());
        return "To complete your registration, please verify your email";
    }

    public void sendRegistrationEmail(String profileId, String email) {
        // send email
        String url = "http://localhost:8080/auth/verification/" + profileId;
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
        emailService.isNotExpiredEmail(entity.getEmail());// check for expireation date

        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }

        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return "Success";
    }

    public String registrationResend(String email) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email not exists");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }
        emailService.isNotExpiredEmail(email);
        sendRegistrationEmail(entity.getId(), email);
        return "To complete your registration please verify your email.";
    }

    public ProfileDTO login(LoginDTO authDTO) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(authDTO.getEmail());
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }

        ProfileEntity entity = optional.get();
        if (!entity.getPassword().equals(MD5Util.getMD5(authDTO.getPassword()))) {
            throw new AppBadException("Wrong password");
        }

        if (entity.getStatus() != ProfileStatus.ACTIVE) {
            throw new AppBadException("User is not active");
        }

        ProfileDTO dto = new ProfileDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setJwt(JWTUtil.encode(entity.getId(),entity.getEmail(), entity.getRole()));
        return dto;
    }
}
