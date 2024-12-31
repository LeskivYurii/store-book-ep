package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.model.enums.AgeGroup;
import com.epam.rd.autocode.spring.project.model.enums.Language;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface BookCriteriaService {

    Specification<Book> buildQuery(String name, String genre, AgeGroup ageGroup, BigDecimal minPrice,
                                   BigDecimal maxPrice, String author, LocalDate publicationDate, Language language);
}
