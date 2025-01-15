package org.company.bookshop.api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "book_category")
public class BookCategoryEntity  {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "book_id")
    String bookId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    BookEntity book;

    @Column(name = "category_id")
    String categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    CategoryEntity category;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();

    public BookCategoryEntity(String bookId, String categoryId) {
        this.bookId = bookId;
        this.categoryId = categoryId;
    }
}
