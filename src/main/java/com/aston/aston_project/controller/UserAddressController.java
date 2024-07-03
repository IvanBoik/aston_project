package com.aston.aston_project.controller;

import com.aston.aston_project.dto.UserAddressDTO;
import com.aston.aston_project.entity.Address;
import com.aston.aston_project.entity.UserAddress;
import com.aston.aston_project.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user_addresses")
public class UserAddressController {

    private final UserAddressService userAddressService;

    @PostMapping
    public UserAddress createRecipe(@RequestBody UserAddressDTO addressDTO) {
        return userAddressService.addUserAddress(addressDTO);
    }

    @GetMapping()
    public List<UserAddressDTO> allUserAddresses() {
        return userAddressService.getAllAddresses();
    }

    @PutMapping("/{id}")
    public void addNewAddress(
            @PathVariable Long id,
            @RequestBody Address address) {
        userAddressService.addNewAddress(id, address);
    }

    @PutMapping("/{id}")
    public void deleteAddress(
            @PathVariable Long id,
            @RequestBody Address address) {
        userAddressService.deleteAddress(id, address);
    }

    @DeleteMapping("/{id}")
    public void  deleteUserAddress(@PathVariable Long id) {userAddressService.deleteUserAddress(id);}
}
