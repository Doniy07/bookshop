package org.company.youtube.entity.profile;

import jakarta.persistence.*;
import lombok.Data;
import org.company.youtube.entity.attach.AttachEntity;
import org.company.youtube.enums.ProfileRole;
import org.company.youtube.enums.ProfileStatus;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "profile")
public class ProfileEntity {

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname" )
    private String surname;

    @Column(name = "email" )
    private String email;

    @Column(name = "temp_email" )
    private String tempEmail;

    @Column(name = "password" )
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileStatus status = ProfileStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ProfileRole role = ProfileRole.ROLE_USER;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "photo_id")
    private String photoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;

}
