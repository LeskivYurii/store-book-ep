package com.epam.rd.autocode.spring.project.dto.response;

import com.epam.rd.autocode.spring.project.model.enums.AgeGroup;
import com.epam.rd.autocode.spring.project.model.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBookListResponse {

    private Long id;
    private String name;
    private String genre;
    private AgeGroup ageGroup;
    private BigDecimal price;
    private String author;
    private Language language;

}
