package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.aop.userwatcher.Delete;
import com.kodilla.ecommercee.aop.userwatcher.Save;
import com.kodilla.ecommercee.aop.userwatcher.Update;
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

    @Autowired
    private OrderDbService service;

    @GetMapping(value = "orders")
    public List<OrderDto> getOrders() {
        return service.getAllOrders();
    }

    @GetMapping(value = "order")
    public OrderDto getOrder(@RequestParam Long orderId) throws OrderNotFoundException {
        return service.getOrder(orderId);
    }

    @Save
    @PostMapping(value = "order")
    public OrderDto addNewOrder(@RequestParam Long userId, @RequestBody OrderDto orderDto) throws UserNotFoundException {
        return service.saveOrder(orderDto);
    }

    @Update
    @PutMapping(value = "order")
    public OrderDto updateOrder(@RequestParam Long userId, @RequestBody OrderDto orderDto) throws OrderNotFoundException, UserNotFoundException {
        return service.updateOrder(orderDto);
    }

    @Delete
    @DeleteMapping(value = "order")
    public void deleteOrder(@RequestParam Long userId, @RequestParam Long orderId) {
        service.deleteOrder(orderId);
    }
}
