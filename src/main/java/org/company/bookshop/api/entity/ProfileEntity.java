package org.company.bookshop.api.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.company.bookshop.api.enums.ProfileRole;
import org.company.bookshop.api.enums.ProfileStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileEntity extends BaseEntity {

    @Column(name = "fio", nullable = false)
    String fio;

    @Column(name = "phone", nullable = false)
    String phone;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "balance", nullable = false)
    double balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    ProfileRole role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    ProfileStatus status;

}
