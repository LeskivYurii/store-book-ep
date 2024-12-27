package com.epam.rd.autocode.spring.project.dto.response;

import com.epam.rd.autocode.spring.project.model.enums.AgeGroup;
import com.epam.rd.autocode.spring.project.model.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

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
    private String image;
    private Language language;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public String getCreatedAt() {
        if(createdAt != null) {
            return DateTimeFormatter.ofPattern("dd.MM.yy HH:mm").format(createdAt);
        } else {
            return null;
        }
    }

    public String getUpdatedAt() {
        if (createdAt != null) {
            return DateTimeFormatter.ofPattern("dd.MM.yy HH:mm").format(updatedAt);
        } else {
            return null;
        }
    }
}
