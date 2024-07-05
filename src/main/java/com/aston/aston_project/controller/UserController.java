package com.aston.aston_project.controller;

import com.aston.aston_project.dto.AddressDto;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.service.UserService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PutMapping("/wishList/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void addProductToWishList(Principal principal, @PathVariable @Positive Long productId) {
        System.out.println(principal);
        userService.addProductToWishList(principal.getName(), productId);
    }

    @DeleteMapping("/wishList/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeProductFromWishList(Principal principal, @PathVariable @Positive Long productId) {
        userService.removeProductFromWishList(principal.getName(), productId);
    }

    @GetMapping("/wishList")
    public List<ProductDtoShort> getWishList(Principal principal) {
        return userService.getWishList(principal.getName());
    }

    @PostMapping("/address")
    @ResponseStatus(HttpStatus.OK)
    public void addAddress(Principal principal, @RequestBody AddressDto dto) {
        userService.addAddress(principal.getName(), dto);
    }

    @DeleteMapping("/address/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeAddress(Principal principal, @PathVariable Long id) {
        userService.removeAddress(principal.getName(), id);
    }
}
