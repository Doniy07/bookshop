package org.company.youtube.entity.category;


import jakarta.persistence.*;
import lombok.Data;
import org.company.youtube.entity.video.VideoEntity;
import org.company.youtube.entity.video.VideoTagEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "category")
@Data
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<VideoEntity> videos;

}
