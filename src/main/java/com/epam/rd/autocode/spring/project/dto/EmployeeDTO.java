package com.epam.rd.autocode.spring.project.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO{

    @Email(message = "It's not email!")
    @NotBlank(message = "Email can't be empty!")
    private String email;
    @NotBlank(message = "Password can't be empty or null!")
    @JsonProperty(access = WRITE_ONLY)
    private String password;
    @NotBlank(message = "Name can't be empty or null!")
    private String name;
    @NotBlank(message = "Phone can't be empty or null!")
    private String phone;
    @NotNull(message = "Birthday can't be null!")
    @DateTimeFormat(pattern = "dd.MM.yyyy", fallbackPatterns = {"dd-MM-yyyy", "MM.dd.yyyy", "MM-dd-yyyy"})
    @Past(message = "Birthdate can't be in present or future time!")
    private LocalDate birthDate;

}
