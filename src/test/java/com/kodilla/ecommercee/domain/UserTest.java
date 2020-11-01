package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.CartDao;
import com.kodilla.ecommercee.repository.OrderDao;
import com.kodilla.ecommercee.repository.UserDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private OrderDao orderDao;

    @Test
    public void saveUserTest() {

        //given
        User user = new User();
        user.setPassword("Hasło");
        user.setUserKey("Key");
        user.setUsername("Michał");

        //when
        userDao.save(user);
        long id = user.getUserId();
        Optional<User> optionalUser = userDao.findById(id);

        //then
        Assert.assertTrue(optionalUser.isPresent());

        //clean up
        userDao.deleteById(id);
    }

    @Test
    public void deleteUserTest() {

        //given
        User user = new User();
        user.setPassword("Hasło");
        user.setUserKey("Key");
        user.setUsername("Michał");

        //when
        userDao.save(user);
        long id = user.getUserId();
        boolean wasSaved = userDao.existsById(id);

        userDao.deleteById(id);
        boolean isExist = userDao.existsById(id);

        //then
        Assert.assertTrue(wasSaved);
        Assert.assertFalse(isExist);

        //clean up
    }

    @Test
    public void updateUserTest() {

        //given
        User user = new User();
        user.setPassword("Hasło");
        user.setUserKey("Key");
        user.setUsername("Michał");
        userDao.save(user);
        long userId = user.getUserId();
        String oldPassword = user.getPassword();

        //when
        user.setPassword("New Password");
        userDao.save(user);

        //then
        Assert.assertNotEquals(oldPassword, user.getPassword());
        Assert.assertEquals("New Password", user.getPassword());

        //clean up
        userDao.delete(user);
    }

    @Test
    public void readUserTest() {

        //given
        User user = new User();
        user.setPassword("New Password");
        user.setUserKey("Key");
        user.setUsername("New Name");
        userDao.save(user);
        long userId = user.getUserId();

        //when
        User readUser = userDao.findById(userId).get();

        //then
        Assert.assertEquals(readUser.getPassword(), "New Password");
        Assert.assertEquals(readUser.getUserKey(), "Key");
        Assert.assertEquals(readUser.getUsername(), "New Name");

        //clean up
        userDao.delete(user);
    }

    @Test
    public void relationWithOrdersTest() {
        //given
        Order order = new Order();
        User user = new User();
        user.setPassword("New Password");
        user.setUserKey("Key");
        user.setUsername("New Name");
        order.setUser(user);
        user.setOrders(Collections.singletonList(order));

        //when
        userDao.save(user);
        long orderId = order.getOrderId();
        boolean orderWasPresent = orderDao.existsById(orderId);

        userDao.delete(user);
        boolean orderIsPresentAfterRemoveUser = orderDao.existsById(orderId);

        //then
        Assert.assertTrue(orderWasPresent);
        Assert.assertFalse(orderIsPresentAfterRemoveUser);

        //cleanUp
    }

    @Test
    public void relationWithCartsTest() {
        //given
        Cart cart = new Cart();
        User user = new User();
        user.setPassword("New Password");
        user.setUserKey("Key");
        user.setUsername("New Name");
        cart.setUser(user);
        user.setCart(cart);
        userDao.save(user);

        long cartId = cart.getCartId();

        //when
        boolean cartWasPresent = cartDao.existsById(cartId);
        userDao.delete(user);
        boolean cartIsPresentAfterRemoveUser = cartDao.existsById(cartId);

        //then
        Assert.assertTrue(cartWasPresent);
        Assert.assertFalse(cartIsPresentAfterRemoveUser);

        //cleanUp
    }

    @Test
    public void compareIdOfOrderTest() {
        //given
        Order order = new Order();
        User user = new User();
        user.setPassword("New Password");
        user.setUserKey("Key");
        user.setUsername("New Name");
        order.setUser(user);
        user.setOrders(Collections.singletonList(order));

        //when
        userDao.save(user);

        //then
        Assert.assertTrue(user.getOrders().contains(order));
        Assert.assertEquals(order.getOrderId(), user.getOrders().get(0).getOrderId());

        //cleanUp
        userDao.delete(user);
    }

    @Test
    public void compareIdOfCartTest() {
        //given
        Cart cart = new Cart();
        User user = new User();
        user.setPassword("New Password");
        user.setUserKey("Key");
        user.setUsername("New Name");
        cart.setUser(user);
        user.setCart(cart);

        //when
        userDao.save(user);

        //then
        Assert.assertEquals(cart, user.getCart());
        Assert.assertEquals(cart.getCartId(), user.getCart().getCartId());

        //cleanUp
        userDao.delete(user);

    }
}