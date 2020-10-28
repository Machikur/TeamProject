package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.dto.OrderDto;
import com.kodilla.ecommercee.exception.order.OrderNotFoundException;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.service.OrderDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class OrderController {
    @Autowired
    private OrderDbService service;

    @Autowired
    private OrderMapper mapper;

    @GetMapping(value = "orders")
    public List<OrderDto> getOrders() {
        return mapper.mapToOrderDtoList(service.getAllOrders());
    }

    @GetMapping(value = "order")
    public OrderDto getOrder(@RequestParam Long orderId) throws OrderNotFoundException {
        return mapper.mapToOrderDto(service.getOrder(orderId).orElseThrow(OrderNotFoundException::new));
    }

    @PostMapping(value = "order", consumes = APPLICATION_JSON_VALUE)
    public void addNewOrder(@RequestBody OrderDto orderDto) throws UserNotFoundException {
        service.saveOrder(mapper.mapToOrder(orderDto));
    }

    @PutMapping(value = "order")
    public OrderDto updateOrder(@RequestBody OrderDto orderDto) throws UserNotFoundException {
        return mapper.mapToOrderDto(service.saveOrder(mapper.mapToOrder(orderDto)));
    }

    @DeleteMapping(value = "order")
    public void deleteOrder(@RequestParam Long orderId) {
        service.deleteOrder(orderId);
    }
}
