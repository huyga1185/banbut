package com.web.banbut.service;

import com.web.banbut.entity.Cart;
import com.web.banbut.entity.User;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.CartRepository;
import com.web.banbut.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public void createCart(User user) {
        if (!userRepository.existsByUsername(user.getUsername()))
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        else if (cartRepository.existsByUser_Username(user.getUsername()))
            throw new AppException(ErrorCode.CART_EXISTED);
        cartRepository.save(new Cart(user));
    }
}
