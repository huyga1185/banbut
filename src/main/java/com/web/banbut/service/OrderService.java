package com.web.banbut.service;

import com.web.banbut.dto.request.OrderRequest;
import com.web.banbut.entity.*;
import com.web.banbut.repository.AddressRepository;
import com.web.banbut.repository.PenVariantRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.web.banbut.dto.response.OrderResponse;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.OrderRepository;
import com.web.banbut.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PenVariantRepository penVariantRepository;
    private final AddressRepository addressRepository;

    public OrderService(
            OrderRepository orderRepository,
            UserRepository userRepository,
            PenVariantRepository penVariantRepository,
            AddressRepository addressRepository
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.penVariantRepository = penVariantRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public OrderResponse createOrder(Authentication authentication, OrderRequest orderRequest) {
        User user = userRepository.findByUsername(authentication.getName())
            .orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
            );

        PenVariant penVariant = penVariantRepository.findById(orderRequest.getPenVariantId())
                .orElseThrow(
                        () -> new AppException(ErrorCode.PEN_VARIANT_NOT_FOUND)
                );

        if (!penVariant.isVisible())
            throw new AppException(ErrorCode.PEN_VARIANT_NOT_FOUND);

        if (penVariant.getReversedQuantity() + orderRequest.getQuantity() > penVariant.getQuantity())
            throw new AppException(ErrorCode.OUT_OF_STOCK);

        Address address = addressRepository.findById(orderRequest.getAddressID())
                .orElseThrow(
                        () -> new AppException(ErrorCode.ADDRESS_NOT_FOUND)
                );

        if (!address.isVisibility())
            throw new AppException(ErrorCode.ADDRESS_NOT_FOUND);

        Order order = new Order(
            user,
            penVariant,
            "Pending",
            orderRequest.getQuantity(),
            penVariant.getPrice() * orderRequest.getQuantity(),
            address,
            orderRequest.getNote()
        );

        try {
            order = orderRepository.save(order);
        } catch (Exception e) {
            throw new AppException(ErrorCode.COULD_NOT_CREATE_ORDER);
        }

        try {
            penVariant.setReversedQuantity(penVariant.getReversedQuantity() + orderRequest.getQuantity());
            penVariantRepository.save(penVariant);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return new OrderResponse(
            order.getOrderId(),
            penVariant.getPenVariantId(),
            address.getAddressID(),
            order.getNote(),
            order.getStatus(),
            order.getQuantity(),
            order.getTotalPrice()
        );
    }

    @Transactional
    public OrderResponse cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
                );

        if (!order.getStatus().equals("Pending"))
            throw new AppException(ErrorCode.COULD_NOT_CANCEL_ORDER);

        PenVariant penVariant = penVariantRepository.findById(order.getPenVariant().getPenVariantId())
            .orElseThrow(
                    () -> new AppException(ErrorCode.PEN_VARIANT_NOT_FOUND)
            );

        penVariant.setReversedQuantity(penVariant.getReversedQuantity() - order.getQuantity());

        order.setStatus("Cancelled");

        order.setUpdatedAt(LocalDateTime.now());

        try {
            order = orderRepository.save(order);
        } catch (Exception e) {
            throw new AppException(ErrorCode.COULD_NOT_CANCEL_ORDER);
        }

        try {
            penVariantRepository.save(penVariant);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return new OrderResponse(
            order.getOrderId(),
            order.getPenVariant().getPenVariantId(),
            order.getAddress().getAddressID(),
            order.getNote(),
            order.getStatus(),
            order.getQuantity(),
            order.getTotalPrice()
        );
    }

    @Transactional
    public OrderResponse updateOrder(String orderId, OrderRequest orderRequest) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
            );

        if (!order.getStatus().equals("Pending"))
            throw new AppException(ErrorCode.COULD_NOT_UPDATE_ORDER);

        if (!orderRequest.getPenVariantId().isEmpty()) {
            PenVariant oldPenVariant = order.getPenVariant();

            PenVariant newPenVariant = penVariantRepository.findById(orderRequest.getPenVariantId())
                .orElseThrow(
                    () -> new AppException(ErrorCode.PEN_VARIANT_NOT_FOUND)
                );

            if (newPenVariant.getReversedQuantity() + order.getQuantity() > newPenVariant.getQuantity())
                throw new AppException(ErrorCode.OUT_OF_STOCK);

            oldPenVariant.setReversedQuantity(oldPenVariant.getReversedQuantity() - order.getQuantity());

            newPenVariant.setReversedQuantity(newPenVariant.getReversedQuantity() + order.getQuantity());

            order.setPenVariant(newPenVariant);

            try {
                penVariantRepository.save(oldPenVariant);
                penVariantRepository.save(newPenVariant);
            } catch (Exception e) {
                throw new AppException(ErrorCode.COULD_NOT_UPDATE_ORDER);
            }
        } else if(!orderRequest.getAddressID().isEmpty()) {
            Address newAddress = addressRepository.findById(orderRequest.getAddressID())
                .orElseThrow(
                    () -> new AppException(ErrorCode.ADDRESS_NOT_FOUND)
                );

            order.setAddress(newAddress);
        } else if (!orderRequest.getNote().isEmpty())
            order.setNote(orderRequest.getNote());
        else if (orderRequest.getQuantity() != 0)
            order.setQuantity(orderRequest.getQuantity());

        try {
            order = orderRepository.save(order);
        } catch (Exception e) {
            throw new AppException(ErrorCode.COULD_NOT_UPDATE_ORDER);
        }
        return new OrderResponse(
            order.getOrderId(),
            order.getPenVariant().getPenVariantId(),
            order.getAddress().getAddressID(),
            order.getNote(),
            order.getStatus(),
            order.getQuantity(),
            order.getTotalPrice()
        );
    }

    @Transactional
    public OrderResponse completeOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
            );
        if (!order.getStatus().equals("Accepted"))
            throw new AppException(ErrorCode.COULD_NOT_COMPLETE_ORDER);
        order.setStatus("Completed");
        order.setUpdatedAt(LocalDateTime.now());
        try {
            order = orderRepository.save(order);
        } catch (Exception e) {
            throw new AppException(ErrorCode.COULD_NOT_COMPLETE_ORDER);
        }
        return new OrderResponse(
            order.getOrderId(),
            order.getPenVariant().getPenVariantId(),
            order.getAddress().getAddressID(),
            order.getNote(),
            order.getStatus(),
            order.getQuantity(),
            order.getTotalPrice()
        );
    }

    @Transactional
    public OrderResponse acceptOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
            );
        if (!order.getStatus().equals("Pending"))
            throw new AppException(ErrorCode.COULD_NOT_ACCEPT_ORDER);
        order.setStatus("Accepted");
        order.setUpdatedAt(LocalDateTime.now());
        try {
            order = orderRepository.save(order);
        } catch (Exception e) {
            throw new AppException(ErrorCode.COULD_NOT_ACCEPT_ORDER);
        }
        return new OrderResponse(
            order.getOrderId(),
            order.getPenVariant().getPenVariantId(),
            order.getAddress().getAddressID(),
            order.getNote(),
            order.getStatus(),
            order.getQuantity(),
            order.getTotalPrice()
        );
    }

    private List<OrderResponse> parseOrder(List<Order> orders) {
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (Order order : orders) {
            orderResponseList.add(new OrderResponse(
                order.getOrderId(),
                order.getPenVariant().getPenVariantId(),
                order.getAddress().getAddressID(),
                order.getNote(),
                order.getStatus(),
                order.getQuantity(),
                order.getTotalPrice()
            ));
        }
        return orderResponseList;
    }

    public List<OrderResponse> getOrderList(Authentication authentication) {
        List<Order> orders = orderRepository.findAllByUser_userId(authentication.getName());
        return parseOrder(orders);
    }

    public OrderResponse getOrderResponse(String orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
            );
        return new OrderResponse(
            order.getOrderId(),
            order.getPenVariant().getPenVariantId(),
            order.getAddress().getAddressID(),
            order.getNote(),
            order.getStatus(),
            order.getQuantity(),
            order.getTotalPrice()
        );
    }

    public List<OrderResponse> adminGetOrderList() {
        List<Order> orders = orderRepository.findAll();
        return parseOrder(orders);
    }
}