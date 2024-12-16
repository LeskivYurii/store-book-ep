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

    @NotNull(message = "Book can't be null!")
    private String bookName;
    @Min(value = 1, message = "Quantity of product can't be zero or less!")
    private Integer quantity;

}
