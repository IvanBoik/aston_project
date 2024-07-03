package com.aston.aston_project.service;

import com.aston.aston_project.dto.UserAddressDTO;
import com.aston.aston_project.entity.Address;
import com.aston.aston_project.entity.UserAddress;
import com.aston.aston_project.mapper.UserAddressMapper;
import com.aston.aston_project.repository.AddressRepository;
import com.aston.aston_project.repository.UserAddressRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAddressService {

    private final UserAddressRepository userAddressRepository;
    private final UserAddressMapper userAddressMapper;
    private final AddressRepository addressRepository;

    public UserAddress addUserAddress(UserAddressDTO dto) {
        return userAddressRepository.save(userAddressMapper.toEntity(dto));
    }

    public List<UserAddressDTO> getAllAddresses() {
        return userAddressRepository.findAll().stream().map(userAddressMapper::toDto).toList();
    }

    public List<UserAddressDTO>getAllAddressesByUserId(Long id) {
        return userAddressRepository.getAllUserAddressesByUserId(id);
    }

    @Transactional
    public void addNewAddress(Long id, Address address) {
        UserAddress userAddress = userAddressRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("User address with id " + id + " not found"));

        if (addressRepository.existsById(address.getId())) {
            userAddress.getAddresses().add(address);
        } else {
            new NotFoundDataException("Address with id " + id + " not found");
        }
        userAddressRepository.save(userAddress);
    }

    @Transactional
    public void deleteAddress(Long id, Address address) {
        UserAddress userAddress = userAddressRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("User address with id " + id + " not found"));

        if (addressRepository.existsById(address.getId())) {
            userAddress.getAddresses().stream().filter(a -> !a.equals(address)).toList();
        } else {
            new NotFoundDataException("Address with id " + id + " not found");
        }
        userAddressRepository.save(userAddress);
    }

    public void deleteUserAddress(Long id) {
        userAddressRepository.deleteById(id);
    }
}
