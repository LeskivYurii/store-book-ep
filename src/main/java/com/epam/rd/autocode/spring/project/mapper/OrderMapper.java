package com.epam.rd.autocode.spring.project.mapper;

import com.epam.rd.autocode.spring.project.dto.response.GetOrderDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetOrderListResponse;
import com.epam.rd.autocode.spring.project.exception.CreateOrderException;
import com.epam.rd.autocode.spring.project.mapper.helper.OrderMapperHelper;
import com.epam.rd.autocode.spring.project.model.CartItem;
import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.model.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = OrderMapperHelper.class)
public interface OrderMapper {

    @Mapping(target = "client", source = "clientEmail", qualifiedByName = "toClient")
    @Mapping(target = "bookItems", source = "cartItemList", qualifiedByName = "toBookItems")
    @Mapping(target = "totalPrice", source = "cartItemList", qualifiedByName = "toPrice")
    @Mapping(target = "status", constant = "NEW")
    Order toOrder(List<CartItem> cartItemList, String clientEmail);

    @Mapping(target = "clientEmail", source = "client.email")
    @Mapping(target = "employeeEmail", source = "employee.email")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "bookItems", source = "bookItems", qualifiedByName = "toGetBookItemResponse")
    GetOrderDetailsResponse toGetOrderDetailsResponse(Order order);

    @Mapping(target = "clientEmail", source = "client.email")
    @Mapping(target = "employeeEmail", source = "employee.email")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "status", source = "status")
    GetOrderListResponse toGetOrderListResponse(Order order);

    @AfterMapping
    default void afterMapping(@MappingTarget Order order) {
        Client client = order.getClient();
        if(client.getBalance().compareTo(order.getTotalPrice()) < 0) {
            throw new CreateOrderException("You don't have enough money on your balance to order books!");
        }
        order.getBookItems().forEach(bookItem -> bookItem.setOrder(order));
        client.setBalance(client.getBalance().subtract(order.getTotalPrice()));
    }


}
