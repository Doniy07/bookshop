package org.company.youtube.controller.email;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.email.EmailDTO;
import org.company.youtube.dto.email.EmailFilterDTO;
import org.company.youtube.service.email.EmailService;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email_history")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;


    @PostMapping("/adm/filter")
    public ResponseEntity<PageImpl<EmailDTO>> filter(@RequestBody EmailFilterDTO filter,
                                                      @RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(emailService.filter(filter, page - 1, size));
    }
}
