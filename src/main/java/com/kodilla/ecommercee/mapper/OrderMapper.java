package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.dto.OrderDto;
import com.kodilla.ecommercee.service.ProductDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDbService productDbService;

    public Order mapToOrder(final OrderDto orderDto) {
        return new Order(
                orderDto.getOrderId(),
                orderDto.getUser(),
                productMapper.mapToProductList(orderDto.getProducts()));
    }

    public OrderDto mapToOrderDto(final Order order) {
        return new OrderDto(
                order.getOrderId(),
                order.getUser(),
                productMapper.mapToProductDtoList(
                        productDbService.findAllProductsByIdList(
                                productMapper.getListOfProductsId(order.getProducts()))));
    }

    public List<OrderDto> mapToOrderDtoList(final List<Order> orderList) {
        return orderList.stream()
                .map(o -> new OrderDto(o.getOrderId(), o.getUser(), productMapper.mapToProductDtoList(o.getProducts())))
                .collect(Collectors.toList());
    }

    public List<Order> mapToOrderList(final List<OrderDto> orderDtoList) {
        return orderDtoList.stream()
                .map(od -> new Order(od.getOrderId(), od.getUser(), productMapper.mapToProductList(od.getProducts())))
                .collect(Collectors.toList());
    }


}
