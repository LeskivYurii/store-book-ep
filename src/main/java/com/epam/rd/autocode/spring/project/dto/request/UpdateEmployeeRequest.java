package com.epam.rd.autocode.spring.project.dto.request;

import com.epam.rd.autocode.spring.project.dto.response.GetEmployeeDetailsResponse;
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
public class UpdateEmployeeRequest {

    @Email(message = "{email.validation}!")
    @NotBlank(message = "{email.validation.notBlank}")
    @UniqueEmail
    private String email;
    @EqualsAndHashCode.Exclude
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$|^$",
            message = "{password.validation.regex}")
    private String password;
    @EqualsAndHashCode.Exclude
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$|^$",
            message = "{password.validation.regex}")
    private String confirmationPassword;
    @EqualsAndHashCode.Exclude
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$|^$",
            message = "{password.validation.regex}")
    private String oldPassword;
    @NotBlank(message = "{name.validation.notBlank}")
    private String name;
    @NotBlank(message = "{phone.validation.notBlank}")
    private String phone;
    @NotNull(message = "{birthday.validation.notNull}")
    @DateTimeFormat(pattern = "dd/MM/yyyy", fallbackPatterns = {"yyyy-MM-dd","dd-MM-yyyy", "MM/dd/yyyy", "MM-dd-yyyy",
            "dd.MM.yyyy", "dd.MM.yyyy", "MM.dd.yyyy"})
    @Past(message = "{birthday.validation.past}")
    private LocalDate birthDate;

    public UpdateEmployeeRequest(GetEmployeeDetailsResponse getEmployeeDetailsResponse) {
        this.email = getEmployeeDetailsResponse.getEmail();
        this.name = getEmployeeDetailsResponse.getName();
        this.birthDate = getEmployeeDetailsResponse.getBirthDate();
        this.phone = getEmployeeDetailsResponse.getPhone();
    }

}

