package com.web.banbut.service;

import com.web.banbut.dto.response.CartItemResponse;
import com.web.banbut.entity.CartItem;
import com.web.banbut.entity.PenVariant;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.CartItemRepository;
import com.web.banbut.dto.request.CartItemRequest;
import com.web.banbut.repository.UserRepository;
import com.web.banbut.repository.CartRepository;
import com.web.banbut.repository.PenVariantRepository;
import com.web.banbut.entity.User;
import com.web.banbut.entity.Cart;
// import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PenVariantRepository penVariantRepository;

    public CartItemService(CartItemRepository cartItemRepository, UserRepository userRepository, CartRepository cartRepository, PenVariantRepository penVariantRepository) {
            this.cartItemRepository = cartItemRepository;
            this.userRepository = userRepository; 
            this.cartRepository = cartRepository;
            this.penVariantRepository = penVariantRepository;
    }

    //wait
    public CartItemResponse addCartItem(Authentication authentication, CartItemRequest cartItemRequest) {
        if (cartItemRequest.getPenVariantID().isEmpty())
            throw new AppException(ErrorCode.LACK_OF_REQUEST_BODY);
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));;
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        PenVariant penVariant = penVariantRepository.findById(cartItemRequest.getPenVariantID()).orElseThrow(() -> new AppException(ErrorCode.PEN_VARIANT_NOT_FOUND));
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setPenVariant(penVariant);
        if (penVariant.getQuantity() < cartItemRequest.getQuantity())
            throw new AppException(ErrorCode.OUT_OF_STOCK);
        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setCreatedAt(LocalDateTime.now());
        cartItem.setUpdatedAt(LocalDateTime.now());
        cartItem = cartItemRepository.save(cartItem);
        return new CartItemResponse(cartItem.getCartItemId(), cart.getCartId(), penVariant.getPen().getName(), penVariant.getPrice(), cartItem.getQuantity(), penVariant.getPrice() * cartItem.getQuantity());
    }

    public void deleteCartItemById(String cartItemId) {
        try {
            cartItemRepository.deleteById(cartItemId);
        } catch (Exception e) {
            throw new AppException(ErrorCode.COULD_NOT_DELETE_CART_ITEM);
        }
    }

    public CartItemResponse updateQuantity(String cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));
        cartItem.setQuantity(quantity);
        cartItem = cartItemRepository.save(cartItem);
        return new CartItemResponse(
                cartItem.getCartItemId(),
                cartItem.getCart().getCartId(),
                cartItem.getPenVariant().getPen().getName(),
                cartItem.getPenVariant().getPrice(),
                cartItem.getQuantity(),
                cartItem.getPenVariant().getPrice() * cartItem.getQuantity()
        );
    }

    public List<CartItemResponse> getListCartItemByCartId(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        List<CartItem> cartItems = (List<CartItem>) cartItemRepository.findAllByCart_CartId(cart.getCartId());
        List<CartItemResponse> cartItemResponses = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            if (!cartItem.getPenVariant().isVisible() && !cartItem.getPenVariant().getPen().isVisible())
                continue;
            cartItemResponses.add(new CartItemResponse(
                    cartItem.getCartItemId(),
                    cartItem.getCart().getCartId(),
                    cartItem.getPenVariant().getPen().getName(),
                    cartItem.getPenVariant().getPrice(),
                    cartItem.getQuantity(),
             cartItem.getPenVariant().getPrice() * cartItem.getQuantity()
            ));
        }
        return cartItemResponses;
    }
}
