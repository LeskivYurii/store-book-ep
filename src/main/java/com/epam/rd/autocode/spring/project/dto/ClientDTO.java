package com.epam.rd.autocode.spring.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@AllArgsConstructor
@NotNull
@NoArgsConstructor
public class ClientDTO{

    @Email(message = "It's not email!")
    @NotBlank(message = "Email can't be empty!")
    private String email;
    @NotBlank(message = "Password can't be empty or null!")
    @JsonProperty(access = WRITE_ONLY)
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}",
            message = "Must be minimum 6 characters, at least one letter and one number")
    private String password;
    @NotBlank(message = "Name can't be empty or null!")
    private String name;
    @DecimalMin(value = "0.0", inclusive = false, message = "Balance can't be zero or less!")
    private BigDecimal balance;

}
