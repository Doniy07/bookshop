package org.company.youtube.service.email;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.email.EmailDTO;
import org.company.youtube.dto.email.EmailFilterDTO;
import org.company.youtube.dto.filter.FilterResponseDTO;
import org.company.youtube.entity.email.EmailEntity;
import org.company.youtube.exception.AppBadException;
import org.company.youtube.repository.email.EmailCustomRepository;
import org.company.youtube.repository.email.EmailRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;

    private final EmailCustomRepository emailCustomRepository;

    public EmailDTO toDTO(EmailEntity entity) {
        EmailDTO dto = new EmailDTO();
        dto.setId(entity.getId());
        dto.setToEmail(entity.getToEmail());
        dto.setTitle(entity.getTitle());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public void checkEmailLimit(String email) {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusMinutes(2);

        long count = emailRepository.countByToEmailAndCreatedDateBetween(email, from, to);
        if (count >= 3) {
            throw new AppBadException("Email limit exceeded. Please try after some time");
        }
    }

    public void isNotExpiredEmail(String email) {
        Optional<EmailEntity> optional = emailRepository.findTop1ByToEmailOrderByCreatedDateDesc(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email history not found");
        }
        EmailEntity entity = optional.get();
        if (entity.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new AppBadException("Confirmation time expired");
        }
    }

    public PageImpl<EmailDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmailEntity> pageObj = emailRepository.findAll(pageable);

        List<EmailDTO> dtoList = pageObj.getContent().stream()
                .map(this::toDTO)
                .toList();

        long totalCount = pageObj.getTotalElements();

        return new PageImpl<EmailDTO>(dtoList, pageable, totalCount);

    }

    public PageImpl<EmailDTO> pagination(int page, int size, String email) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmailEntity> pageObj = emailRepository.findAllByToEmail(email, pageable);

        List<EmailDTO> dtoList = pageObj.getContent().stream()
                .map(this::toDTO)
                .toList();

        long totalCount = pageObj.getTotalElements();

        return new PageImpl<EmailDTO>(dtoList, pageable, totalCount);

    }

    public void create(String email, String title, String text) {
        EmailEntity entity = new EmailEntity();
        entity.setToEmail(email);
        entity.setTitle(title);
        entity.setMessage(text);
        emailRepository.save(entity);
    }

    public PageImpl<EmailDTO> filter(EmailFilterDTO filter, int page, int size) {
        FilterResponseDTO<EmailEntity> filterResponse = emailCustomRepository.filter(filter, page, size);


        List<EmailDTO> dtoList = new LinkedList<>();
        for (EmailEntity entity : filterResponse.getContent()) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<EmailDTO>(dtoList, PageRequest.of(page, size), filterResponse.getTotalCount());
    }

}
