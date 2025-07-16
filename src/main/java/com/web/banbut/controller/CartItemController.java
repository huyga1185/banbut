package com.web.banbut.controller;

import com.web.banbut.dto.response.ApiResponse;
import com.web.banbut.service.CartItemService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart-item")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/add-item/{penVariantId}/{quantity}")
    public ApiResponse<Map<String, Object>> addCartItem(@AuthenticationPrincipal Jwt jwt, @PathVariable String penVariantId, @PathVariable int quantity) {
        String username = jwt.getSubject();
        return new ApiResponse<>(
                "success",
                Map.of("cart-item", cartItemService.addCartItem(username, penVariantId,  quantity))
        );
    }

    @DeleteMapping("/{cartItemId}")
    public ApiResponse<Map<String, Object>> deleteCartItemById(@PathVariable String cartItemId) {
        cartItemService.deleteCartItemById(cartItemId);
        return new ApiResponse<>(
                "success",
                Map.of(
                        "message", "Item deleted"
                )
        );
    }

    @PatchMapping("/update/{cartItemId}/{quantity}")
    public ApiResponse<Map<String, Object>> updateQuantity(@PathVariable String cartItemId, @PathVariable int quantity) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "cart_item", cartItemService.updateQuantity(cartItemId, quantity)
                )
        );
    }

    @GetMapping("/get-items")
    public ApiResponse<Map<String, Object>> getListCartItemByCartId(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        return new ApiResponse<>(
                "success",
                Map.of(
                        "cart-items", cartItemService.getListCartItemByCartId(username)
                )
        );
    }
}
