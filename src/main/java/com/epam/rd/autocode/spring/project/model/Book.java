package com.epam.rd.autocode.spring.project.model;

import com.epam.rd.autocode.spring.project.model.enums.AgeGroup;
import com.epam.rd.autocode.spring.project.model.enums.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOKS")
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String genre;
    @Column(name = "age_group")
    @Enumerated(value = EnumType.STRING)
    private AgeGroup ageGroup;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(name = "publication_year")
    private LocalDate publicationDate;
    @Column(nullable = false)
    private String author;
    @Column(name = "number_of_pages", nullable = false)
    private Integer pages;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private String characteristics;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Language language;
    @Column(name="created_at", nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

}
