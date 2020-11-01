package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.dto.OrderDto;
import com.kodilla.ecommercee.exception.order.OrderNotFoundException;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.repository.OrderDao;
import com.kodilla.ecommercee.validation.AuthorizationRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderDbService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserDbService userDbService;

    public List<OrderDto> getAllOrders() {
        return orderMapper.mapToOrderDtoList(orderDao.findAll());
    }

    @AuthorizationRequired
    public OrderDto saveOrder(Long userId, OrderDto orderDto) throws UserNotFoundException {
        Order order = orderMapper.mapToOrder(orderDto);
        User user = userDbService.findById(orderDto.getUserId());
        order.setUser(user);
        user.getOrders().add(order);
        return orderMapper.mapToOrderDto(orderDao.save(order));
    }

    public OrderDto getOrder(final Long id) throws OrderNotFoundException {
        return orderMapper.mapToOrderDto(orderDao.findById(id).orElseThrow(OrderNotFoundException::new));
    }

    @AuthorizationRequired
    public void deleteOrder(Long userId, final Long id) {
        orderDao.deleteById(id);
    }
}
