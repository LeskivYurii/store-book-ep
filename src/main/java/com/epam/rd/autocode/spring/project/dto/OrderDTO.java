package com.epam.rd.autocode.spring.project.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO{

    @Email(message = "It's not email!")
    @NotNull(message = "Client email can't be empty!")
    private String clientEmail;
    @Email(message = "It's not email!")
    @NotNull(message = "Employee Email can't be empty!")
    private String employeeEmail;
    @FutureOrPresent(message = "Order date can't be in past!")
    private LocalDateTime orderDate;
    @DecimalMin(value = "0.0", inclusive = false, message = "Order price can't be zero or less!")
    private BigDecimal price;
    @NotEmpty(message = "Order can't be created without items!")
    private List<BookItemDTO> bookItems;

}
