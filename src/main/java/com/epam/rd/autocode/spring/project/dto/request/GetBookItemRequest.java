package com.epam.rd.autocode.spring.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBookItemRequest {

    private Long id;
    private String bookName;
    private Integer quantity;
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

}
