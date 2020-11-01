package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.dto.CartDto;
import com.kodilla.ecommercee.dto.OrderDto;
import com.kodilla.ecommercee.exception.order.CartNotFoundException;
import com.kodilla.ecommercee.exception.product.ProductNotFoundException;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.repository.CartDao;
import com.kodilla.ecommercee.repository.OrderDao;
import com.kodilla.ecommercee.repository.ProductDao;
import com.kodilla.ecommercee.validation.AuthorizationRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;


@Service
@Transactional
public class CartDbService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDbService userDbService;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    public CartDto getCartById(Long id) throws CartNotFoundException {
        return cartMapper.mapToCartDto(cartDao.findById(id).orElseThrow(() -> new CartNotFoundException(id)));
    }

    @AuthorizationRequired
    public CartDto createCart(Long userId, CartDto cartDto) throws UserNotFoundException {
        Cart cart = cartMapper.mapToCart(cartDto);
        User user = userDbService.findById(userId);
        cart.setProducts(productMapper.mapToProductList(cartDto.getProducts()));
        cart.setUser(userDbService.findById(userId));
        user.setCart(cart);
        return cartMapper.mapToCartDto(cartDao.save(cart));
    }

    @AuthorizationRequired
    public CartDto addProductToCart(Long userId, Long cartId, Long productId) throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartDao.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        Product product = productDao.findById(productId).orElseThrow(ProductNotFoundException::new);
        cart.getProducts().add(product);
        product.getCarts().add(cart);

        return cartMapper.mapToCartDto(cartDao.save(cart));
    }

    @AuthorizationRequired
    public CartDto deleteProductFromCart(Long userId, Long cartId, Long productId) throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartDao.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        cart.getProducts().remove(productDao.findById(productId).orElseThrow(ProductNotFoundException::new));
        return cartMapper.mapToCartDto(cartDao.save(cart));
    }

    @AuthorizationRequired
    public OrderDto createOrder(Long userId, Long cartId) throws CartNotFoundException, UserNotFoundException {
        Cart cart = cartDao.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        User user = userDbService.findById(userId);

        Order order = new Order();
        order.setUser(user);
        order.setProducts(cart.getProducts());
        cart.setProducts(new ArrayList<>());
        return orderMapper.mapToOrderDto(orderDao.save(order));
    }
}
