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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;


@Service
@Transactional
public class CartDbService {

    private CartDao cartDao;
    private ProductDao productDao;
    private OrderDao orderDao;
    private UserDbService userDbService;
    private CartMapper cartMapper;
    private ProductMapper productMapper;
    private OrderMapper orderMapper;

    @Autowired
    public CartDbService(CartDao cartDao, ProductDao productDao, OrderDao orderDao,
                         UserDbService userDbService, CartMapper cartMapper,
                         ProductMapper productMapper, OrderMapper orderMapper) {
        this.cartDao = cartDao;
        this.productDao = productDao;
        this.orderDao = orderDao;
        this.userDbService = userDbService;
        this.cartMapper = cartMapper;
        this.productMapper = productMapper;
        this.orderMapper = orderMapper;
    }

    public CartDto getCartById(Long id) throws CartNotFoundException {
        return cartMapper.mapToCartDto(cartDao.findById(id).orElseThrow(() -> new CartNotFoundException(id)));
    }

    public CartDto createCart(CartDto cartDto) throws UserNotFoundException {
        Cart cart = cartMapper.mapToCart(cartDto);
        User user = userDbService.findById(cart.getCartId());
        cart.setProducts(productMapper.mapToProductList(cartDto.getProducts()));
        cart.setUser(userDbService.findById(cart.getCartId()));
        user.setCart(cart);
        return cartMapper.mapToCartDto(cartDao.save(cart));
    }


    public CartDto addProductToCart(Long cartId, Long productId) throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartDao.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        Product product = productDao.findById(productId).orElseThrow(ProductNotFoundException::new);
        cart.getProducts().add(product);
        product.getCarts().add(cart);

        return cartMapper.mapToCartDto(cartDao.save(cart));
    }

    public CartDto deleteProductFromCart(Long cartId, Long productId) throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartDao.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        cart.getProducts().remove(productDao.findById(productId).orElseThrow(ProductNotFoundException::new));
        return cartMapper.mapToCartDto(cartDao.save(cart));
    }

    public OrderDto createOrder(Long cartId) throws CartNotFoundException, UserNotFoundException {
        Cart cart = cartDao.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        User user = userDbService.findById(cart.getUser().getUserId());

        Order order = new Order();
        order.setUser(user);
        order.setProducts(cart.getProducts());
        cart.setProducts(new ArrayList<>());
        return orderMapper.mapToOrderDto(orderDao.save(order));
    }
}
