package com.epam.rd.autocode.spring.project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookItemDTO {

    @NotNull(message = "{book.validation}")
    private Long bookId;
    @Min(value = 1, message = "{book.amount.validation.min}")
    private Integer quantity;

    public BookItemDTO create() {
        return new BookItemDTO();
    }
}
