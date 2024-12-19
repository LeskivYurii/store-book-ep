package com.epam.rd.autocode.spring.project.dto.request;

import com.epam.rd.autocode.spring.project.validation.annotation.ValidPassword;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidPassword
public class CreateClientRequest {

    @Email(message = "It's not email!")
    @NotBlank(message = "Email can't be empty!")
    private String email;
    @NotBlank(message = "Password can't be empty or null!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$",
            message = "Password must contain at least one number, lower case and upper case letters and be 6 symbols long!")
    private String password;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$",
            message = "Password must contain at least one number, lower case and upper case letters and be 6 symbols long!")
    private String confirmationPassword;
    @NotBlank(message = "Name can't be empty or null!")
    private String name;
    @DecimalMin(value = "0.0", inclusive = false, message = "Balance can't be zero or less!")
    private BigDecimal balance;


}
