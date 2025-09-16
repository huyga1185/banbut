package com.web.banbut.controller;

import com.web.banbut.dto.response.ApiResponse;
import com.web.banbut.dto.request.OrderRequest;
import com.web.banbut.dto.response.OrderResponse;
import com.web.banbut.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(
            OrderService orderService
    ) {
        this.orderService = orderService;
    }

    @PostMapping
    public ApiResponse<Map<String, Object>> createOrder(Authentication authentication, @RequestBody OrderRequest orderRequest) {
        return new ApiResponse<Map<String, Object>>(
                "success",
                Map.of(
                        "order: ", orderService.createOrder(authentication, orderRequest)
                )
        );
    }

    @PatchMapping("/cancel-order/{orderId}")
    public ApiResponse<Map<String, Object>> cancelOrder(@PathVariable String orderId) {
        return new ApiResponse<Map<String, Object>>(
                "success",
                Map.of(
                        "order: ", orderService.cancelOrder(orderId)
                )
        );
    }

    @PatchMapping("/{orderId}")
    public ApiResponse<Map<String, Object>> updateOrder(@PathVariable String orderId, @RequestBody OrderRequest orderRequest) {
        return new ApiResponse<Map<String, Object>>(
                "success",
                Map.of(
                        "order: ", orderService.updateOrder(orderId, orderRequest)
                )
        );
    }

    @GetMapping("/list")
    public ApiResponse<Map<String, Object>> getOrderList(Authentication authentication) {
        return new ApiResponse<Map<String, Object>>(
                "success",
                Map.of(
                        "orders: ", orderService.getOrderList(authentication)
                )
        );
    }

    @GetMapping("/{orderId}")
    public ApiResponse<Map<String, Object>> getOrder(@PathVariable String orderId) {
        return new ApiResponse<Map<String, Object>>(
                "success",
                Map.of(
                        "order: ", orderService.getOrderResponse(orderId)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ApiResponse<Map<String, Object>> adminGetListOrder() {
        return new ApiResponse<Map<String, Object>>(
                "success",
                Map.of(
                        "orders: ", orderService.adminGetOrderList()
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/accept-order/{orderId}")
    public ApiResponse<Map<String, Object>> acceptOrder(@PathVariable String orderId) {
        return new ApiResponse<Map<String, Object>>(
                "success",
                Map.of(
                        "order: ", orderService.acceptOrder(orderId)
                )
        );
    }

    @PatchMapping("/complete-order/{orderId}")
    public ApiResponse<Map<String, Object>> completeOrder(@PathVariable String orderId) {
        return new ApiResponse<Map<String, Object>>(
                "success",
                Map.of(
                        "order: ", orderService.completeOrder(orderId)
                )
        );
    }
}
