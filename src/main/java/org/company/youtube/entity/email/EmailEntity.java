package org.company.youtube.entity.email;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "email_history")
public class EmailEntity {
    @Id
    @UuidGenerator
    private String id;
    @Column(name = "to_email")
    private String toEmail;
    @Column(name = "title")
    private String title;
    @Column(name = "message", columnDefinition = "text")
    private String message;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
