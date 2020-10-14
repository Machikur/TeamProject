package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.repository.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDbService {
    @Autowired
    private OrderDao orderDao;

    public List<Order> getAllOrders() {
        return orderDao.findAll();
    }

    public Order saveOrder(final Order order) {
        return orderDao.save(order);
    }

    public Optional<Order> getOrder(final Long id) {
        return orderDao.findById(id);
    }

    public void deleteOrder(final Long id) {
        orderDao.deleteById(id);
    }
}
