package com.epam.rd.autocode.spring.project.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeDetailsResponse {

    private Long id;
    private String email;
    private String name;
    private String phone;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;
    private boolean isActive;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
