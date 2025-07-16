package com.web.banbut.repository;

import com.web.banbut.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    Collection<CartItem> findAllByCart_CartId(String cartId);

    Optional<CartItem> findByCart_CartIdAndPenVariant_PenVariantId(String cartId, String penVariantId);
}
