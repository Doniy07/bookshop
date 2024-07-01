package org.company.youtube.controller.profile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.profile.ProfileCreateDTO;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.dto.profile.ProfileUpdateDTO;
import org.company.youtube.service.profile.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    //    	1. Change password

    @PutMapping("/current/change-password")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Boolean> changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String newPasswordRepeat) {
        return ResponseEntity.ok().body(profileService.changePassword(oldPassword, newPassword, newPasswordRepeat));
    }

    //    	2. Update Email (with email verification)

    @PutMapping("/current/update-email/{email}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> updateEmail(
            @PathVariable("email") String newEmail) {
        return ResponseEntity.ok().body(profileService.updateEmail(newEmail));
    }

    @Operation(summary = "Verification", description = "Api for auth Verification")
    @GetMapping("/verification/{userId}")
    public ResponseEntity<String> verificationByEmail(@PathVariable("userId") String userId) {
        String response = profileService.emailUpdateVerification(userId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Resend", description = "Api for auth Resend")
    @GetMapping("/resend/{email}")
    public ResponseEntity<String> resend(@PathVariable("email") String email) {
        String body = profileService.resend(email);
        return ResponseEntity.ok().body(body);
    }

//	    3. Update Profile Detail(name,surname)

    @PutMapping("/current/update")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ProfileDTO> update(@Valid @RequestBody ProfileUpdateDTO profile) {
        return ResponseEntity.ok().body(profileService.update(profile));
    }

//	    4. Update Profile Attach (main_photo) (delete old attach

    @PutMapping("/current/update-attach")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ProfileDTO> updatePhoto(@RequestParam("image") MultipartFile file) {
        return ResponseEntity.ok().body(profileService.updatePhoto(file));
    }

//	    5. Get Profile Detail (id,name,surname,email,main_photo((url)))

    @GetMapping("/user/current")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ProfileDTO> getUserDetail() {
        return ResponseEntity.ok().body(profileService.getUserDetail());
    }
//      6. Create Profile (ADMIN)
//         (id,name,surname,email,Role(ADMIN,MODERATOR))

    @PostMapping("/adm/create") // ADMIN
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileCreateDTO profile) {
        ProfileDTO response = profileService.create(profile);
        return ResponseEntity.ok().body(response);
    }
}
