package com.epam.rd.autocode.spring.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetClientDetailsResponse {

    private String email;
    private String name;
    private BigDecimal balance;
    private String phone;
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

    public String getUpdatedAt() {
        if (createdAt != null) {
            return DateTimeFormatter.ofPattern("dd.MM.yy HH:mm").format(updatedAt);
        } else {
            return null;
        }
    }
}
