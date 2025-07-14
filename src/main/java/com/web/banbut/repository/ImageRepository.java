package com.web.banbut.repository;

import com.web.banbut.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    boolean existsByName(String name);
    Optional<Image> findByName(String name);
}
