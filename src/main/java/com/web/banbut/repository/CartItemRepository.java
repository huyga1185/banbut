package com.web.banbut.repository;

import com.web.banbut.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    Collection<CartItem> findAllByCart_CartId(String cartId);
}
