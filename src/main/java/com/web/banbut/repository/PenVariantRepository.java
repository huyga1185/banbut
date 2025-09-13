package com.web.banbut.repository;

import com.web.banbut.entity.PenVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;

@Repository
public interface PenVariantRepository extends JpaRepository<PenVariant, String> {
    Collection<PenVariant> findAllByPen_PenId(String penId);

    @Transactional
    @Modifying
    @Query("UPDATE PenVariant AS PV SET PV.visible = false WHERE PV.pen.penId = :penId")
    void disableAllByPenId(String penId);
}
