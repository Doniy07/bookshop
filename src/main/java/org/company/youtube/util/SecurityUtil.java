package org.company.youtube.util;


import org.company.youtube.config.CustomUserDetail;
import org.company.youtube.dto.auth.JwtDTO;
import org.company.youtube.entity.profile.ProfileEntity;
import org.company.youtube.enums.ProfileRole;
import org.company.youtube.exception.AppForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static JwtDTO getJwtDTO(String token) {
        String jwt = token.substring(7); // Bearer eyJhb
        return JWTUtil.decode(jwt);
    }

    public static JwtDTO getJwtDTO(String token, ProfileRole requiredRole) {
         // Bearer eyJhb
        JwtDTO dto = getJwtDTO(token);
        if (!dto.getRole().equals(requiredRole)) {
            throw new AppForbiddenException("You are not allowed to add profile");
        }
        return dto;
    }

    public static String getProfileId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
        return user.profile().getId();
    }

    public static ProfileEntity getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
        return user.profile();
    }
}
