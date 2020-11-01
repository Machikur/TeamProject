package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.dto.OrderDto;
import com.kodilla.ecommercee.service.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserDbService userDbService;

    public Order mapToOrder(final OrderDto orderDto) {
        return new Order(
                productMapper.mapToProductList(orderDto.getProducts()));
    }

    public OrderDto mapToOrderDto(final Order order) {
        return new OrderDto(
                order.getOrderId(),
                order.getUser().getUserId(),
                productMapper.mapToProductDtoList(order.getProducts()));
    }

    public List<OrderDto> mapToOrderDtoList(final List<Order> orderList) {
        return orderList.stream()
                .map(o -> new OrderDto(o.getOrderId(), o.getUser().getUserId(), productMapper.mapToProductDtoList(o.getProducts())))
                .collect(Collectors.toList());
    }


}
