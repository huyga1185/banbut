package com.web.banbut.controller;

import com.web.banbut.dto.response.ApiResponse;
import com.web.banbut.service.CartItemService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart-item")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
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

    @GetMapping("/{cartId}")
    public ApiResponse<Map<String, Object>> getListCartItemByCartId(@PathVariable String cartId) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "cart-items", cartItemService.getListCartItemByCartId(cartId)
                )
        );
    }
}
