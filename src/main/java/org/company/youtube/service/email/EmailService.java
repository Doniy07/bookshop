package org.company.youtube.service.email;

import org.company.youtube.entity.email.EmailEntity;
import org.company.youtube.exception.AppBadException;
import org.company.youtube.repository.email.EmailRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailService {

    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

//    public List<EmailDTO> getByEmail(String email) {
//        return emailRepository.findAllByEmail(email)
//                .stream()
//                .map(this::toDTO)
//                .toList();
//    }
//
//    public List<EmailDTO> getByDate(LocalDate date) {
//        return emailRepository.findByCreatedDate(date,date.plusDays(1))
//                .stream()
//                .map(this::toDTO)
//                .toList();
//    }

//   public EmailDTO toDTO(EmailEntity entity) {
//       EmailDTO dto = new EmailDTO();
//       dto.setId(entity.getId());
//       dto.setToEmail(entity.getToEmail());
//       dto.setTitle(entity.getTitle());
//       dto.setMessage(entity.getMessage());
//       dto.setCreatedDate(entity.getCreatedDate());
//       return dto;
//   }

    public void checkEmailLimit(String email) {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusMinutes(2);

        long count = emailRepository.countByToEmailAndCreatedDateBetween(email, from, to);
        if (count >=3) {
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

    


//    public PageImpl<EmailDTO> pagination(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<EmailEntity> pageObj = emailRepository.findAll(pageable);
//
//        List<EmailDTO> dtoList = pageObj.getContent().stream()
//                .map(this::toDTO)
//                .toList();
//
//        long totalCount = pageObj.getTotalElements();
//
//        return new PageImpl<EmailDTO>(dtoList, pageable, totalCount);
//
//    }

    public void create(String email,String title, String text) {
        EmailEntity entity = new EmailEntity();
        entity.setToEmail(email);
        entity.setTitle(title);
        entity.setMessage(text);
        emailRepository.save(entity);
    }
}
