package com.epam.rd.autocode.spring.project.dto.response;

import com.epam.rd.autocode.spring.project.model.enums.AgeGroup;
import com.epam.rd.autocode.spring.project.model.enums.Language;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBookDetailsResponse {

    private Long id;
    private String name;
    private String genre;
    private AgeGroup ageGroup;
    private BigDecimal price;
    private LocalDate publicationDate;
    private String author;
    private Integer pages;
    private Integer quantity;
    private String characteristics;
    private String description;
    private Language language;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
