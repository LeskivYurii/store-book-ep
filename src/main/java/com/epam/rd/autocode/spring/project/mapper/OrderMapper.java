package com.epam.rd.autocode.spring.project.mapper;

import com.epam.rd.autocode.spring.project.dto.OrderDTO;
import com.epam.rd.autocode.spring.project.mapper.helper.OrderMapperHelper;
import com.epam.rd.autocode.spring.project.model.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = OrderMapperHelper.class)
public interface OrderMapper {

    @Mapping(target = "employee", source = "employeeEmail", qualifiedByName = "toEmployee")
    @Mapping(target = "client", source = "clientEmail", qualifiedByName = "toClient")
    @Mapping(target = "orderDate", source = "orderDTO", qualifiedByName = "toOrderDate")
    Order toOrder(OrderDTO orderDTO);

    @Mapping(target = "clientEmail", source = "client.email")
    @Mapping(target = "employeeEmail", source = "employee.email")
    @Mapping(target = "orderDate", source = "orderDate")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "bookItems", source = "bookItems", qualifiedByName = "toBookItemDtos")
    OrderDTO toOrderDto(Order order);

    @AfterMapping
    private void afterMapping(@MappingTarget Order order, OrderDTO orderDTO) {
        order.getBookItems().forEach(bookItem -> bookItem.setOrder(order));
        order.setPrice(toOrderPrice(order));
    }

    private BigDecimal toOrderPrice(Order order) {
        return order.getBookItems()
                .stream()
                .map(bookItem -> bookItem.getBook().getPrice().multiply(BigDecimal.valueOf(bookItem.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
