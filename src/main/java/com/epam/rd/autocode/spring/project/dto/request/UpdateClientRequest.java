package com.epam.rd.autocode.spring.project.dto.request;

import com.epam.rd.autocode.spring.project.dto.response.GetClientDetailsResponse;
import com.epam.rd.autocode.spring.project.validation.annotation.UniqueEmail;
import com.epam.rd.autocode.spring.project.validation.annotation.ValidPassword;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidPassword
public class UpdateClientRequest {

    @Email(message = "It's not email!")
    @UniqueEmail
    private String email;
    @EqualsAndHashCode.Exclude
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$|^$",
            message = "Password must contain at least one number, lower case and upper case letters and be 6 symbols long!")
    private String password;
    @EqualsAndHashCode.Exclude
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$|^$",
            message = "Password must contain at least one number, lower case and upper case letters and be 6 symbols long!")
    private String confirmationPassword;
    @EqualsAndHashCode.Exclude
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$|^$",
            message = "Password must contain at least one number, lower case and upper case letters and be 6 symbols long!")
    private String oldPassword;
    @NotBlank(message = "Name can't be empty or null!")
    private String name;
    @DecimalMin(value = "0.0",  message = "Balance can't be zero or less!")
    private BigDecimal balance;
    @NotBlank(message = "Name can't be empty or null!")
    private String phone;

    public UpdateClientRequest(GetClientDetailsResponse getClientDetailsResponse) {
        this.email = getClientDetailsResponse.getEmail();
        this.name = getClientDetailsResponse.getName();
        this.balance = getClientDetailsResponse.getBalance();
    }

}
