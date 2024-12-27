package com.epam.rd.autocode.spring.project.dto.response;

import com.epam.rd.autocode.spring.project.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderDetailsResponse {

    private Long id;
    private OrderStatus status;
    private OffsetDateTime createdAt;
    private String employeeEmail;
    private String clientEmail;
    private List<GetBookItemResponse> bookItems;
    private BigDecimal totalPrice;

    public String getCreatedAt() {
        if(createdAt != null) {
            return DateTimeFormatter.ofPattern("dd.MM.yy HH:mm").format(createdAt);
        } else {
            return null;
        }
    }

}
