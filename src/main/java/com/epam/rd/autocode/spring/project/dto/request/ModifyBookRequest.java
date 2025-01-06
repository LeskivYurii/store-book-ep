package com.epam.rd.autocode.spring.project.dto.request;

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
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyBookRequest {

    @Min(1)
    private Long id;
    @NotBlank(message = "{book.name.validation}")
    private String name;
    @NotBlank(message = "{book.genre.validation}")
    private String genre;
    @NotNull(message = "{book.ageGroup.validation}")
    private AgeGroup ageGroup;
    @DecimalMin(value = "0.0", inclusive = false, message = "{book.price.validation.min}")
    @NotNull(message = "{book.price.validation.notNull}")
    private BigDecimal price;
    @NotNull(message = "{book.price.publicationDate.notNull}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
    @NotBlank(message = "{book.author.validation.notBlank}")
    private String author;
    @Min(value = 1, message = "{book.pages.validation.min}")
    @NotNull(message = "{book.pages.validation.notNull}")
    private Integer pages;
    @Min(value = 1, message = "{book.amount.validation.min}")
    @NotNull(message = "{book.amount.validation.notNull}")
    private Integer quantity;
    @NotBlank(message = "{book.characteristics.validation.notBlank}")
    private String characteristics;
    @NotBlank(message = "{book.description.validation.notBlank}")
    private String description;
    @NotNull(message = "{book.language.validation.notNull}book.language.validation.notNull")
    private Language language;
    private MultipartFile image;

}
