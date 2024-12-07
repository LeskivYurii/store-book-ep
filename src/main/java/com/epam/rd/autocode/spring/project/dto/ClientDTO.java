package com.epam.rd.autocode.spring.project.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NotNull
@NoArgsConstructor
public class ClientDTO{

    @Email(message = "It's not email!")
    @NotNull(message = "Email can't be empty!")
    private String email;
    @NotBlank(message = "Password can't be empty or null!")
    private String password;
    @NotBlank(message = "Name can't be empty or null!")
    private String name;
    @DecimalMin(value = "0.0", inclusive = false, message = "Balance can't be zero or less!")
    private BigDecimal balance;

}
