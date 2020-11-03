package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.dto.OrderDto;
import com.kodilla.ecommercee.exception.order.OrderNotFoundException;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.repository.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderDbService {

    private OrderDao orderDao;
    private OrderMapper orderMapper;
    private UserDbService userDbService;

    @Autowired
    public OrderDbService(OrderDao orderDao, OrderMapper orderMapper, UserDbService userDbService) {
        this.orderDao = orderDao;
        this.orderMapper = orderMapper;
        this.userDbService = userDbService;
    }

    public List<OrderDto> getAllOrders() {
        return orderMapper.mapToOrderDtoList(orderDao.findAll());
    }

    public OrderDto saveOrder(OrderDto orderDto) throws UserNotFoundException {
        Order order = orderMapper.mapToOrder(orderDto);
        User user = userDbService.findById(orderDto.getUserId());
        order.setUser(user);
        user.getOrders().add(order);
        return orderMapper.mapToOrderDto(orderDao.save(order));
    }

    public OrderDto updateOrder(OrderDto orderDto) throws OrderNotFoundException, UserNotFoundException {
        Order order = orderDao.findById(orderDto.getOrderId()).orElseThrow(OrderNotFoundException::new);
        order.setProducts(orderDto.getProducts().stream()
                .map(productDto -> new Product(productDto.getProductId(), productDto.getProductName(),
                        productDto.getProductPrice(), productDto.getQuantity()))
                .collect(Collectors.toList()));
        order.setUser(userDbService.findById(orderDto.getUserId()));
        return orderMapper.mapToOrderDto(order);
    }

    public OrderDto getOrder(Long orderId) throws OrderNotFoundException {
        return orderMapper.mapToOrderDto(orderDao.findById(orderId).orElseThrow(OrderNotFoundException::new));
    }

    public void deleteOrder(Long orderId) {
        orderDao.deleteById(orderId);
    }
}
