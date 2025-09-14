package com.web.banbut.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.web.banbut.dto.response.OrderResponse;
import com.web.banbut.entity.User;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.OrderRepository;
import com.web.banbut.repository.UserRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public static boolean checkStatusValid(String status) {
        if (!(status.equals("Pending") || status.equals("Accepted") || status.equals("Completed") || status.equals("Cancelled")))
            return true;
        return false;
    }

    public OrderResponse createOrder(Authentication authentication, OrderRequest orderRequest) {
        if (!checkStatusValid(orderRequest.getStatus()))
            throw new AppException(ErrorCode.ORDER_STATUS_INVALID);
        User user = userRepository.findByUsername(authentication.getName())
            .orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
            );
        
        return null;
    }
}
