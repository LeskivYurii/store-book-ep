package com.epam.rd.autocode.spring.project.dto.request;

import com.epam.rd.autocode.spring.project.validation.annotation.UniqueEmail;
import com.epam.rd.autocode.spring.project.validation.annotation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidPassword
public class CreateEmployeeRequest {

    @Email(message = "It's not email!")
    @NotBlank(message = "Email can't be empty!")
    @UniqueEmail
    private String email;
    @EqualsAndHashCode.Exclude
    @NotBlank(message = "Password can't be empty or null!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$",
            message = "Password must contain at least one number, lower case and upper case letters and be 6 symbols" +
                      " long!")
    private String password;
    @EqualsAndHashCode.Exclude
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$",
            message = "Password must contain at least one number, lower case and upper case letters and be 6 symbols" +
                      " long!")
    private String confirmationPassword;
    @NotBlank(message = "Name can't be empty or null!")
    private String name;
    @NotBlank(message = "Phone can't be empty or null!")
    private String phone;
    @NotNull(message = "Birthday can't be null!")
    @DateTimeFormat(pattern = "dd/MM/yyyy", fallbackPatterns = {"yyyy-MM-dd", "dd-MM-yyyy", "MM/dd/yyyy", "MM-dd-yyyy",
            "dd.MM.yyyy", "dd.MM.yyyy", "MM.dd.yyyy"})
    @Past(message = "Birthdate can't be in present or future time!")
    private LocalDate birthDate;

}
