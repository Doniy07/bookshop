package org.company.youtube.controller.profile;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.company.youtube.dto.profile.ProfileCreateDTO;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.dto.profile.ProfileUpdateDTO;
import org.company.youtube.service.profile.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    //    	1. Change password
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/current/change-password")
    public ResponseEntity<Boolean> changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String newPasswordRepeat) {
        return ResponseEntity.ok().body(profileService.changePassword(oldPassword, newPassword, newPasswordRepeat));
    }

    //    	2. Update Email (with email verification)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/current/update-email/{email}")
    public ResponseEntity<String> updateEmail(
            @PathVariable("email") String newEmail) {
        return ResponseEntity.ok().body(profileService.updateEmail(newEmail));
    }
    @Operation(summary = "Verification", description = "Api for auth Verification")
    @GetMapping("/verification/{userId}")
    public ResponseEntity<String> verificationByEmail(@PathVariable("userId") String userId) {
        String response = profileService.verification(userId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Resend", description = "Api for auth Resend")
    @GetMapping("/resend/{email}")
    public ResponseEntity<String> resend(@PathVariable("email") String email) {
        String body = profileService.resend(email);
        return ResponseEntity.ok().body(body);
    }

//	    3. Update Profile Detail(name,surname)

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/current/update")
    public ResponseEntity<ProfileDTO> update(@Valid @RequestBody ProfileUpdateDTO profile) {
        return ResponseEntity.ok().body(profileService.update(profile));
    }

//	    4. Update Profile Attach (main_photo) (delete old attach

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/current/update-attach")
    public ResponseEntity<ProfileDTO> updateAttach(@RequestParam("image") MultipartFile file) {
        return ResponseEntity.ok().body(profileService.updateAttach(file));
    }

//	    5. Get Profile Detail (id,name,surname,email,main_photo((url)))

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/user/current")
    public ResponseEntity<ProfileDTO> getUserDetail() {
        return ResponseEntity.ok().body(profileService.getUserDetail());
    }
//      6. Create Profile (ADMIN)
//         (id,name,surname,email,Role(ADMIN,MODERATOR))
    @PostMapping("/adm/create") // ADMIN
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileCreateDTO profile) {
        ProfileDTO response = profileService.create(profile);
        return ResponseEntity.ok().body(response);
    }
}
