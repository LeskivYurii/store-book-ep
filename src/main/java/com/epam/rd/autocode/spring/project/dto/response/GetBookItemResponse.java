package com.epam.rd.autocode.spring.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetBookItemResponse {

    private Long id;
    private Long bookId;
    private String bookName;
    private Integer quantity;
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

}
