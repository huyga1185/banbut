package com.web.banbut.repository;

import com.web.banbut.entity.Cart;
import com.web.banbut.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    boolean existsByUser_Username(String username);
    Optional<Cart> findByUser(User user);
}
