package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.aop.userwatcher.OperationType;
import com.kodilla.ecommercee.aop.userwatcher.UserOperation;
import com.kodilla.ecommercee.dto.OrderDto;
import com.kodilla.ecommercee.exception.order.OrderNotFoundException;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.service.OrderDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class OrderController {

    private final OrderDbService service;

    @Autowired
    public OrderController(OrderDbService service) {
        this.service = service;
    }

    @GetMapping("/orders")
    public List<OrderDto> getOrders() {
        return service.getAllOrders();
    }

    @GetMapping("/order")
    public OrderDto getOrder(@RequestParam Long orderId) throws OrderNotFoundException {
        return service.getOrder(orderId);
    }

    @UserOperation(operationtype = OperationType.CREATE)
    @PostMapping("/order")
    public OrderDto addNewOrder(@RequestParam Long userId, @RequestBody OrderDto orderDto) throws UserNotFoundException {
        return service.saveOrder(orderDto);
    }

    @UserOperation(operationtype = OperationType.UPDATE)
    @PutMapping("/order")
    public OrderDto updateOrder(@RequestParam Long userId, @RequestBody OrderDto orderDto) throws OrderNotFoundException, UserNotFoundException {
        return service.updateOrder(orderDto);
    }

    @UserOperation(operationtype = OperationType.DELETE)
    @DeleteMapping("/order")
    public void deleteOrder(@RequestParam Long userId, @RequestParam Long orderId) {
        service.deleteOrder(orderId);
    }
}
