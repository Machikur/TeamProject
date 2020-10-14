package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductEntityTestSuite {
    @Autowired
    UserDao userDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    CartDao cartDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    GroupDao groupDao;

    @Test
    public void testProductDaoSave() {

        Product sandals = new Product();

        sandals.setProductName("sandals");
        sandals.setQuantity(1);
        sandals.setProductPrice(150.0);
        productDao.save(sandals);

        long id = sandals.getProductId();
        Optional<Product> readProduct = productDao.findById(id);

        assertTrue(readProduct.isPresent());
        try {
            productDao.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testRelationsBetweenProductAndGroup() {
        List<Product> foodsList = new ArrayList<>();

        Product banana = new Product();
        productDao.save(banana);

        banana.setProductName("banana");
        banana.setQuantity(4);
        banana.setProductPrice(4.0);

        Group foods = new Group();
        groupDao.save(foods);

        foodsList.add(banana);
        foods.setProducts(foodsList);
        banana.setGroup(foods);

        assertEquals(1, foodsList.size());
        assertEquals(banana.getGroup().getId(), foods.getProducts().get(0).getGroup().getId());

        try {
            productDao.deleteAll();
            groupDao.deleteAll();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testRelationsBetweenProductAndCart() {
        Product banana = new Product();

        banana.setProductName("banana");
        banana.setQuantity(4);
        banana.setProductPrice(4.0);

        Cart cart = new Cart();
        cart.getProducts().add(banana);
        banana.getCarts().add(cart);

        productDao.save(banana);
        long bananaId = banana.getProductId();

        assertEquals(banana.getCarts().get(0).getCartId(), cart.getCartId());

        try {
            productDao.deleteById(bananaId);
            cartDao.deleteAll();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testRelationsBetweenProductAndOrder() {
        List<Product> foodsList = new ArrayList<>();

        Product banana = new Product();

        banana.setProductName("banana");
        banana.setQuantity(4);
        banana.setProductPrice(4.0);

        foodsList.add(banana);

        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        orderList.add(order);
        orderDao.save(order);

        order.setProducts(foodsList);
        banana.setOrders(orderList);

        assertEquals(order.getOrderId(), banana.getOrders().get(0).getOrderId());
        try {
            productDao.deleteAll();
            orderDao.deleteAll();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testDeleteProduct() {
        List<Product> foodsList = new ArrayList<>();
        Product banana = new Product();
        banana.setProductName("banana");
        banana.setQuantity(4);
        banana.setProductPrice(4.0);
        productDao.save(banana);

        foodsList.add(banana);

        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        orderList.add(order);
        orderDao.save(order);
        order.setProducts(foodsList);
        banana.setOrders(orderList);

        assertEquals(order.getOrderId(), banana.getOrders().get(0).getOrderId());

        long orderId = order.getOrderId();
        Optional<Order> readOrder = orderDao.findById(orderId);

        Group foods = new Group();
        groupDao.save(foods);
        foodsList.add(banana);
        foods.setProducts(foodsList);
        banana.setGroup(foods);

        long foodsId = foods.getId();
        Optional<Group> readFoods = groupDao.findById(foodsId);

        assertEquals(banana.getGroup().getId(), foods.getProducts().get(0).getGroup().getId());

        Cart cart = new Cart();
        cartDao.save(cart);

        long cartId = cart.getCartId();
        Optional<Cart> readCart = cartDao.findById(cartId);
        cart.getProducts().add(banana);
        banana.getCarts().add(cart);

        assertEquals(banana.getCarts().get(0).getCartId(), cart.getCartId());

        long bananaId = banana.getProductId();
        productDao.deleteById(bananaId);
        Optional<Product> readBanana = productDao.findById(bananaId);

        assertFalse(readBanana.isPresent());
        assertTrue(readOrder.isPresent());
        assertTrue(readFoods.isPresent());
        assertTrue(readCart.isPresent());

        try {
            groupDao.deleteAll();
            orderDao.deleteAll();
            cartDao.deleteAll();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}