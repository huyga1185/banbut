package com.web.banbut.service;

import com.web.banbut.dto.response.CartItemResponse;
import com.web.banbut.entity.CartItem;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.CartItemRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    //wait
    public CartItemResponse addCartItem(Authentication authentication) {
        String username = authentication.name();

        return null;
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

    public List<CartItemResponse> getListCartItemByCartId(String cartId) {
        List<CartItem> cartItems = (List<CartItem>) cartItemRepository.findAllByCart_CartId(cartId);
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
