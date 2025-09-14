package com.web.banbut.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.banbut.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    List<Address> findAllByUser_userId(String userId);
}
