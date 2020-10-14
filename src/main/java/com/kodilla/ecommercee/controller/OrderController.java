package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.dto.OrderDto;
import com.kodilla.ecommercee.exception.order.OrderNotFoundException;
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

    @RequestMapping(method = RequestMethod.GET, value = "getOrders")
    public List<OrderDto> getOrders() {
        return mapper.mapToOrderDtoList(service.getAllOrders());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getOrder")
    public OrderDto getOrder(@RequestParam Long orderId) throws OrderNotFoundException {
        return mapper.mapToOrderDto(service.getOrder(orderId).orElseThrow(OrderNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.POST, value = "addNewOrder", consumes = APPLICATION_JSON_VALUE)
    public void addNewOrder(@RequestBody OrderDto orderDto) {service.saveOrder(mapper.mapToOrder(orderDto));}

    @RequestMapping(method = RequestMethod.PUT, value = "updateOrder")
    public OrderDto updateOrder(@RequestBody OrderDto orderDto) {
        return mapper.mapToOrderDto(service.saveOrder(mapper.mapToOrder(orderDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteOrder")
    public void deleteOrder(@RequestParam Long orderId) {
        service.deleteOrder(orderId);
    }
}
