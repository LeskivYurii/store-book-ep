package com.epam.rd.autocode.spring.project.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeDetailsResponse {

    private Long id;
    private String email;
    private String name;
    private String phone;
    private LocalDate birthDate;
    private boolean isActive;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public String getCreatedAt() {
        if(createdAt != null) {
            return DateTimeFormatter.ofPattern("dd.MM.yy HH:mm").format(createdAt);
        } else {
            return null;
        }
    }

    public String getBirthDateString() {
        if(birthDate != null) {
            return DateTimeFormatter.ofPattern("dd.MM.yy").format(birthDate);
        } else {
            return null;
        }
    }

    public String getUpdatedAt() {
        if (createdAt != null) {
            return DateTimeFormatter.ofPattern("dd.MM.yy HH:mm").format(updatedAt);
        } else {
            return null;
        }
    }
}
