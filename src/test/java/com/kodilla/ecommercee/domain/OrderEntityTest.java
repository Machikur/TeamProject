package com.kodilla.ecommercee.domain;



import com.kodilla.ecommercee.repository.OrderDao;
import com.kodilla.ecommercee.repository.ProductDao;
import com.kodilla.ecommercee.repository.UserDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderEntityTest {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Test
    public void testOrderDaoSave() {

        //Given
        Order order = new Order();
        //When
        orderDao.save(order);
        //Then
        Long id = order.getOrderId();
        Optional<Order> orderTest = orderDao.findById(id);
        Assert.assertTrue(orderTest.isPresent());
        //cleanUp
        orderDao.deleteById(id);

    }

    @Test
    public void findAllOrders() {
        //Given
        User user = new User();
        user.setUsername("Test");
        user.setPassword("Hasło");
        user.setUserKey("key");

        User user2 = new User();
        user2.setUsername("Test2");
        user2.setPassword("Hasło2");
        user2.setUserKey("key2");

        List<Product> products = new ArrayList<>();
        Order order = new Order();
        Order order2 = new Order();
        order.setUser(user);
        order2.setUser(user2);
        order.setProducts(products);
        order2.setProducts(products);
        //When
        orderDao.save(order);
        orderDao.save(order2);
        List<Order> orders = (List<Order>) orderDao.findAll();
        //Then
        Assert.assertEquals(2, orders.size());
        //Cleanup
        orderDao.deleteAll();
        userDao.deleteAll();
    }
    @Test
    public void findOrder() {
        //Given
        User user = new User();
        user.setUsername("Test");
        user.setPassword("Hasło");
        user.setUserKey("key");

        User user2 = new User();
        user2.setUsername("Test2");
        user2.setPassword("Hasło2");
        user2.setUserKey("key2");

        List<Product> products = new ArrayList<>();
        Order order = new Order(user, products);
        Order order2 = new Order(user2, products);
        //When
        orderDao.save(order);
        orderDao.save(order2);

        long id = order2.getOrderId();
        Optional<Order> orders =  orderDao.findById(id);
        //Then
        Assert.assertTrue(orders.isPresent());
        //Cleanup
        orderDao.deleteAll();
        userDao.deleteAll();
    }
    @Test
    public void deleteById() {
        //Given
        User user = new User();
        user.setUsername("Test");
        user.setPassword("Hasło");
        user.setUserKey("key");

        User user2 = new User();
        user2.setUsername("Test2");
        user2.setPassword("Hasło2");
        user2.setUserKey("key2");

        List<Product> products = new ArrayList<>();
        Order order = new Order(user, products);
        Order order2 = new Order(user2, products);
        //When
        orderDao.save(order);
        orderDao.save(order2);

        long id = order2.getOrderId();
        orderDao.deleteById(id);
        List<Order> orders = (List<Order>) orderDao.findAll();
        //Then
        Assert.assertEquals(1,orders.size());
        //Cleanup
        orderDao.deleteAll();
        userDao.deleteAll();
    }
    @Test
    public void relationWithProductsAndUsers() {
        //Given
        User user = new User();
        user.setUsername("Test");
        user.setPassword("Hasło");
        user.setUserKey("key");

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        products.add(product);
        Order order = new Order(user, products);

        //When
        orderDao.save(order);

        long user1 = user.getUserId();
        long productId = product.getProductId();


        long id = order.getOrderId();
        Optional<Order> orderExist = orderDao.findById(id);
        Assert.assertTrue(orderExist.isPresent());

        orderDao.deleteById(id);

        Optional<Product> productExist = productDao.findById(productId);
        Optional<User> userExist = userDao.findById(user1);

        //Then
        Assert.assertTrue(userExist.isPresent());
        Assert.assertTrue(productExist.isPresent());

        Optional<Order> orderExistDelete = orderDao.findById(id);
        Assert.assertFalse(orderExistDelete.isPresent());

        //Clean
        userDao.deleteAll();
        orderDao.deleteAll();
        productDao.deleteAll();

    }
}

