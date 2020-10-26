package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.order.CartNotFoundException;
import com.kodilla.ecommercee.exception.product.ProductNotFoundException;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.repository.CartDao;
import com.kodilla.ecommercee.repository.OrderDao;
import com.kodilla.ecommercee.repository.ProductDao;
import com.kodilla.ecommercee.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartDbService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    public Cart getCartById(final Long id) throws CartNotFoundException {
        return cartDao.findById(id).orElseThrow(() -> new CartNotFoundException(id));
    }

    public Cart createCart(Cart cart) {
        return cartDao.save(cart);
    }

    public Cart addProductToCart(final Long cartId, final Long productId) throws CartNotFoundException, ProductNotFoundException {
        Cart cartToAddProduct = cartDao.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        cartToAddProduct.getProducts().add(productDao.findById(productId).orElseThrow(() -> new ProductNotFoundException()));
        return cartDao.save(cartToAddProduct);
    }

    public Cart deleteProductFromCart(final Long cartId, final Long productId) throws CartNotFoundException, ProductNotFoundException {
        Cart deleteProductFromCart = cartDao.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        deleteProductFromCart.getProducts().remove(productDao.findById(productId).orElseThrow(() -> new ProductNotFoundException()));
        return cartDao.save(deleteProductFromCart);
    }

    public Order createOrder(final long cartId, final long userId) throws CartNotFoundException, UserNotFoundException {
        Cart cart = cartDao.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        Order order = new Order();
        order.setUser(user);
        order.setProducts(cart.getProducts());

        return orderDao.save(order);
    }
}
