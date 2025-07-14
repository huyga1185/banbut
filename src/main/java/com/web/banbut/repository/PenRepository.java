package com.web.banbut.repository;

import com.web.banbut.entity.Pen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenRepository extends JpaRepository<Pen, String> {
    boolean existsPenByBrand_BrandId(String brandId);
    boolean existsPenByCategory_CategoryId(String categoryId);
}
