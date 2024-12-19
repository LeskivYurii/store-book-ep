package com.epam.rd.autocode.spring.project.dto.request;

import com.epam.rd.autocode.spring.project.dto.BookItemDTO;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    @Email(message = "It's not email!")
    @NotNull(message = "Client email can't be empty!")
    private String clientEmail;
    @DecimalMin(value = "0.0", inclusive = false, message = "Order price can't be zero or less!")
    private BigDecimal price;
    @NotEmpty(message = "Order can't be created without items!")
    private List<BookItemDTO> bookItems;

}
