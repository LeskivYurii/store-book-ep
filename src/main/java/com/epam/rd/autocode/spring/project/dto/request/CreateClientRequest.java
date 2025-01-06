package com.epam.rd.autocode.spring.project.dto.request;

import com.epam.rd.autocode.spring.project.validation.annotation.ValidPassword;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidPassword
public class CreateClientRequest {

    @Email(message = "{email.validation}")
    @NotBlank(message = "{email.validation.notBlank}")
    private String email;
    @EqualsAndHashCode.Exclude
    @NotBlank(message = "{password.validation.notBlank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$",
            message = "{password.validation.regex}")
    private String password;
    @EqualsAndHashCode.Exclude
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$",
            message = "{password.validation.regex}")
    private String confirmationPassword;
    @NotBlank(message = "{name.validation.notBlank}")
    private String name;
    @DecimalMin(value = "0.0", inclusive = false, message = "{balance.validation.min}")
    private BigDecimal balance;
    @NotBlank(message = "{phone.validation.notBlank}")
    private String phone;

}
