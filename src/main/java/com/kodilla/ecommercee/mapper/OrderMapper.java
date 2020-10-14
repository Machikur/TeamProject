package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public Order mapToOrder(final OrderDto orderDto) {
        return new Order(
                orderDto.getOrderId(),
                orderDto.getUser(),
                orderDto.getProducts());
    }

    public OrderDto mapToOrderDto(final Order order) {
        return new OrderDto(
                order.getOrderId(),
                order.getUser(),
                order.getProducts());
    }

    public List<OrderDto> mapToOrderDtoList(final List<Order> orderList) {
        return orderList.stream()
                .map(o -> new OrderDto(o.getOrderId(), o.getUser(), o.getProducts()))
                .collect(Collectors.toList());
    }

    public List<Order> mapToOrderList(final List<OrderDto> orderDtoList) {
        return orderDtoList.stream()
                .map(od -> new Order(od.getOrderId(), od.getUser(), od.getProducts()))
                .collect(Collectors.toList());
    }
}
