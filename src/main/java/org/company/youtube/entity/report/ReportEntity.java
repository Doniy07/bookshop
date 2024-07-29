package org.company.youtube.entity.report;

import jakarta.persistence.*;
import lombok.*;
import org.company.youtube.entity.profile.ProfileEntity;
import org.company.youtube.enums.ReportType;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report_entity")
public class ReportEntity {

    @Id
    @UuidGenerator
    String id;

    @Column(name = "profile_id")
    private Long profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(columnDefinition = "text")
    private String content;

    @Column(name = "entity_id")
    private String entityId;

    @Enumerated(EnumType.STRING)
    private ReportType type;

    @Builder.Default
    @Column(name = "visible")
    private Boolean visible=Boolean.TRUE;

    @Builder.Default
    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();

}