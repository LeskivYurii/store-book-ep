package com.epam.rd.autocode.spring.project.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCartItemRequest {

    private Long id;
    @NotNull
    @Min(1)
    private Long bookId;
    @NotNull
    @Min(1)
    private Integer quantity;
    @Email(message = "It's not an email!")
    @NotBlank
    private String clientEmail;
}
