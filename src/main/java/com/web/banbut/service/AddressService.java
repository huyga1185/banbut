package com.web.banbut.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.web.banbut.dto.request.AddressRequest;
import com.web.banbut.dto.response.AddressCreationResponse;
import com.web.banbut.dto.response.AddressResponse;
import com.web.banbut.entity.Address;
import com.web.banbut.entity.User;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.AddressRepository;
import com.web.banbut.repository.UserRepository;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(
        AddressRepository addressRepository,
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public AddressCreationResponse createAddress(Authentication authentication, AddressRequest addressRequest) {
        User user = userRepository.findByUsername(authentication.getName())
            .orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
            );
        Address address = new Address();
        address.setAddress(addressRequest.getAddress());
        address.setDistrict(addressRequest.getDistrict());
        address.setName(addressRequest.getName());
        address.setPhoneNumber(addressRequest.getPhoneNumber());
        address.setProvince(addressRequest.getProvince());
        address.setWard(addressRequest.getWard());
        address.setUser(user);
        address.setVisibility(true);
        address.setCreatedAt(LocalDateTime.now());
        address.setUpdatedAt(LocalDateTime.now());
        try {
            address = addressRepository.save(address);
        } catch (Exception e) {
            System.out.println("[ERROR]: " + e);
            throw new AppException(ErrorCode.COULD_NOT_CREATE_ADDRESS);
        }
        return new AddressCreationResponse(
            address.getAddressID(), 
            address.getAddress(), 
            address.getDistrict(), 
            address.getName(), 
            address.getProvince(),
            address.getWard(), 
            address.getUser().getUserId(), 
            address.getPhoneNumber(), 
            address.getCreatedAt(),
            address.getUpdatedAt()
        );
    }

    public List<AddressResponse> getListAddress(Authentication authentication) {
        List<AddressResponse> responses = new ArrayList<>();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(
                    () -> new AppException(ErrorCode.USER_NOT_FOUND)
                );
        List<Address> addresses = addressRepository.findAllByUser_userId(user.getUserId());
        for (Address address : addresses) {
            if (!address.isVisibility())
                continue;
            responses.add(new AddressResponse(
                address.getAddressID(), 
                address.getAddress(), 
                address.getDistrict(), 
                address.getName(), 
                address.getProvince(),
                address.getWard(), 
                address.getUser().getUserId(), 
                address.getPhoneNumber(), 
                address.getCreatedAt(), 
                address.getUpdatedAt()
            ));
        }
        return responses;
    }

    public AddressResponse getAddress(String addressID) {
        Address address = addressRepository.findById(addressID)
            .orElseThrow(
                () -> new AppException(ErrorCode.ADDRESS_NOT_FOUND)
            );
        if (!address.isVisibility())
            throw new AppException(ErrorCode.ADDRESS_NOT_FOUND);
        return new AddressResponse(
            address.getAddressID(), 
            address.getAddress(), 
            address.getDistrict(), 
            address.getName(), 
            address.getProvince(),
            address.getWard(), 
            address.getUser().getUserId(), 
            address.getPhoneNumber(), 
            address.getCreatedAt(), 
            address.getUpdatedAt()
        );
    }

    public void deleteAddress(String addressID) {
        Address address = addressRepository.findById(addressID)
            .orElseThrow(
                () -> new AppException(ErrorCode.ADDRESS_NOT_FOUND)
            );
        if (!address.isVisibility())
            throw new AppException(ErrorCode.ADDRESS_NOT_FOUND);
        if (address.getOrders() == null || address.getOrders().isEmpty()) {
            try {
                addressRepository.delete(address);
            } catch (Exception e) {
                throw new AppException(ErrorCode.COULD_NOT_DELETE_ADDRESS);
            }
        } else {
            address.setVisibility(false);
            try {
                addressRepository.save(address);
            } catch (Exception e) {
                throw new AppException(ErrorCode.COULD_NOT_DELETE_ADDRESS);
            }
        }
    }

    public AddressResponse updateAddress(String addressID, AddressRequest request) {
        Address address = addressRepository.findById(addressID)
            .orElseThrow(
                () -> new AppException(ErrorCode.ADDRESS_NOT_FOUND)
            );
        if (!address.isVisibility())
            throw new AppException(ErrorCode.ADDRESS_NOT_FOUND);
        else if (request.getAddress() != null)
            address.setAddress(request.getAddress());
        else if (request.getDistrict() != null)
            address.setDistrict(request.getDistrict());
        else if (request.getName() != null)
            address.setName(request.getName());
        else if (request.getWard() != null)
            address.setWard(request.getWard());
        else if (request.getPhoneNumber() == 0)
            address.setPhoneNumber(request.getPhoneNumber());
        
        address.setUpdatedAt(LocalDateTime.now());
        try {
            address = addressRepository.save(address);
        } catch (Exception e) {
            throw new AppException(ErrorCode.COULD_NOT_UPDATE_ADDRESS);
        }

        return new AddressResponse(
            address.getAddressID(), 
            address.getAddress(), 
            address.getDistrict(), 
            address.getName(), 
            address.getProvince(),
            address.getWard(), 
            address.getUser().getUserId(), 
            address.getPhoneNumber(), 
            address.getCreatedAt(), 
            address.getUpdatedAt()
        );
    }
}
