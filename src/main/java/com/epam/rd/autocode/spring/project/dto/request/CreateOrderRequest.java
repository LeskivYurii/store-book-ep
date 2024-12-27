package com.epam.rd.autocode.spring.project.dto.request;

import com.epam.rd.autocode.spring.project.dto.BookItemDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    @Email(message = "It's not email!")
    @NotNull(message = "Client email can't be empty!")
    private String clientEmail;
    @NotEmpty(message = "Order can't be created without items!")
    private List<BookItemDTO> bookItems = new ArrayList<>();

}
