package org.company.youtube.entity.tag;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.company.youtube.entity.video.VideoTagEntity;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tag")
public class TagEntity {

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
    private List<VideoTagEntity> videos;
}
