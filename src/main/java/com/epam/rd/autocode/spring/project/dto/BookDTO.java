package com.epam.rd.autocode.spring.project.dto;

import com.epam.rd.autocode.spring.project.model.enums.AgeGroup;
import com.epam.rd.autocode.spring.project.model.enums.Language;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO{

    @NotBlank(message = "Name of the book can't be empty or null!")
    private String name;
    @NotBlank(message = "Genre of the book can't be empty or null!")
    private String genre;
    @NotNull(message = "Age group of the book can't be null!")
    private AgeGroup ageGroup;
    @DecimalMin(value = "0.0", inclusive = false, message = "Price of the book can't be zero or less!")
    private BigDecimal price;
    @NotNull(message = "Publication date of the book can't be null!")
        @DateTimeFormat(pattern = "dd.MM.yyyy", fallbackPatterns = {"dd-MM-yyyy", "MM.dd.yyyy", "MM-dd-yyyy"})
    private LocalDate publicationDate;
    @NotBlank(message = "Author of the book can't be empty or null!")
    private String author;
    @Min(value = 1, message = "Pages of the book can't be zero or less!")
    private Integer pages;
    @NotBlank(message = "Characteristics of the book can't be empty or null!")
    private String characteristics;
    @NotBlank(message = "Description of the book can't be empty or null!")
    private String description;
    @NotNull(message = "Language of the book can't be null!")
    private Language language;
}
