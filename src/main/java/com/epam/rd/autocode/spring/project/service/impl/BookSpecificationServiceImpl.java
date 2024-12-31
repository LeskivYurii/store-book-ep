package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.model.enums.AgeGroup;
import com.epam.rd.autocode.spring.project.model.enums.Language;
import com.epam.rd.autocode.spring.project.service.BookCriteriaService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class BookSpecificationServiceImpl implements BookCriteriaService {

    @Override
    public Specification<Book> buildQuery(String name, String genre, AgeGroup ageGroup, BigDecimal minPrice,
                                          BigDecimal maxPrice, String author, LocalDate publicationDate,
                                          Language language) {

        return Specification.where(like("name", name)
                .and(like("genre", genre))
                .and(equals("language", language != null? language.name() : null))
                .and(equals("publicationDate", publicationDate))
                .and(equals("ageGroup",  ageGroup != null? ageGroup.name() : null))
                .and(like("author", author))
                .and(between("price", minPrice, maxPrice)));
    }

    private Specification<Book> like(String field, String name) {
        return (book, cg, cb) -> {
            if (name == null || name.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(book.get(field)), "%" + name.toLowerCase() + "%");
        };
    }

    private Specification<Book> between(String field, BigDecimal min, BigDecimal max) {
        return (book, cg, cb) -> {
            if (min == null && max == null) {
                return cb.conjunction();
            } else if (min == null) {
                return cb.lessThanOrEqualTo(book.get(field), max);
            } else if (max == null) {
                return cb.greaterThanOrEqualTo(book.get(field), min);
            }
            return cb.between(book.get(field), min, max);
        };
    }

    private Specification<Book> equals(String field, Object value) {
        return (book, cg, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            if(value instanceof String string) {
                return cb.equal(cb.lower(book.get(field)), string.toLowerCase());
            }
            return cb.equal(book.get(field), value);
        };
    }


}
