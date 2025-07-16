package com.web.banbut.service;

import com.web.banbut.dto.response.CartItemResponse;
import com.web.banbut.entity.*;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.*;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PenVariantRepository penVariantRepository;
    private final ImageRepository imageRepository;
    private final FileStorageService fileStorageService;

    public CartItemService(CartItemRepository cartItemRepository, UserRepository userRepository, CartRepository cartRepository, PenVariantRepository penVariantRepository, ImageRepository imageRepository, FileStorageService fileStorageService) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.penVariantRepository = penVariantRepository;
        this.imageRepository = imageRepository;
        this.fileStorageService = fileStorageService;
    }

    public CartItemResponse addCartItem(String username, String penVariantId, int quantity) {
        if (quantity <= 0) {
            throw new AppException(ErrorCode.INVALID_QUANTITY); // Bạn nên định nghĩa thêm mã lỗi này
        }

//        String username = authentication.name();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Cart cart = cartRepository.findCartByUser_Username(username)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        PenVariant penVariant = penVariantRepository.findById(penVariantId)
                .orElseThrow(() -> new AppException(ErrorCode.PEN_VARIANT_NOT_FOUND));
        List<Image> images = (List<Image>) imageRepository.findAllByPen_PenId(penVariant.getPen().getPenId());
        Image image = null;
        if (!images.isEmpty()) {
            image = images.getFirst();
        }
        // Kiểm tra tồn kho
        if (quantity > penVariant.getQuantity()) {
            throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
        }

        // Kiểm tra xem item đã có trong cart chưa
        Optional<CartItem> existingItemOpt = cartItemRepository.findByCart_CartIdAndPenVariant_PenVariantId(
                cart.getCartId(), penVariantId
        );

        CartItem cartItem;
        if (existingItemOpt.isPresent()) {
            cartItem = existingItemOpt.get();
            int totalQuantity = cartItem.getQuantity() + quantity;

            if (totalQuantity > penVariant.getQuantity()) {
                throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
            }

            cartItem.setQuantity(totalQuantity);
        } else {
            cartItem = new CartItem(cart, penVariant, quantity);
        }

        cartItem = cartItemRepository.save(cartItem);

        return new CartItemResponse(
                cartItem.getCartItemId(),
                cartItem.getCart().getCartId(),
                penVariant.getPen().getName(),
                penVariant.getPrice(),
                cartItem.getQuantity(),
                cartItem.getQuantity() * penVariant.getPrice(),
                image != null ? fileStorageService.buildFileResponseFromStoredFile(image.getName()).getUrl() : null
        );
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
        PenVariant penVariant = cartItem.getPenVariant();
        if (penVariant.getQuantity() < quantity)
            throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
        return new CartItemResponse(
                cartItem.getCartItemId(),
                cartItem.getCart().getCartId(),
                cartItem.getPenVariant().getPen().getName(),
                cartItem.getPenVariant().getPrice(),
                cartItem.getQuantity(),
                cartItem.getPenVariant().getPrice() * cartItem.getQuantity(),
                null
        );
    }

    public List<CartItemResponse> getListCartItemByCartId(String username) {
        String cartId = cartRepository.findCartByUser_Username(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)).getCartId();
        List<CartItem> cartItems = (List<CartItem>) cartItemRepository.findAllByCart_CartId(cartId);
        List<CartItemResponse> cartItemResponses = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            if (!cartItem.getPenVariant().isVisible() && !cartItem.getPenVariant().getPen().isVisible())
                continue;
            PenVariant penVariant = penVariantRepository.findById(cartItem.getPenVariant().getPenVariantId()).orElseThrow(() -> new AppException(ErrorCode.PEN_VARIANT_NOT_FOUND));
            List<Image> images = (List<Image>) imageRepository.findAllByPen_PenId(penVariant.getPen().getPenId());
            Image image = null;
            if (!images.isEmpty()) {
                image = images.getFirst();
            }
            cartItemResponses.add(new CartItemResponse(
                    cartItem.getCartItemId(),
                    cartItem.getCart().getCartId(),
                    cartItem.getPenVariant().getPen().getName(),
                    cartItem.getPenVariant().getPrice(),
                    cartItem.getQuantity(),
             cartItem.getPenVariant().getPrice() * cartItem.getQuantity(),
                    image != null ? fileStorageService.buildFileResponseFromStoredFile(image.getName()).getUrl() : null
            ));
        }
        return cartItemResponses;
    }
}
