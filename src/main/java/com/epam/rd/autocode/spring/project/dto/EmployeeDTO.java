package com.epam.rd.autocode.spring.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO{

    @Email(message = "It's not email!")
    @NotNull
    private String email;
    @NotBlank(message = "Password can't be empty or null!")
    private String password;
    @NotBlank(message = "Name can't be empty or null!")
    private String name;
    @NotBlank(message = "Phone can't be empty or null!")
    private String phone;
    @NotNull(message = "Birthday can't be null!")
    @DateTimeFormat(pattern = "dd.MM.yyyy", fallbackPatterns = {"dd-MM-yyyy", "MM.dd.yyyy", "MM-dd-yyyy"})
    private LocalDate birthDate;

}
