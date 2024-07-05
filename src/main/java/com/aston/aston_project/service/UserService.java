package com.aston.aston_project.service;


import com.aston.aston_project.dto.AddressDto;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.dto.SignUpRequest;
import com.aston.aston_project.dto.util.ProductDtoMapping;
import com.aston.aston_project.entity.Address;
import com.aston.aston_project.entity.Product;
import com.aston.aston_project.entity.User;
import com.aston.aston_project.feign.client.YandexSearchLocationClient;
import com.aston.aston_project.feign.dto.YandexResponse;
import com.aston.aston_project.jwt.JwtUtils;
import com.aston.aston_project.repository.AddressRepository;
import com.aston.aston_project.repository.ProductRepository;
import com.aston.aston_project.repository.RoleRepository;
import com.aston.aston_project.repository.UserRepository;
import com.aston.aston_project.util.LocationUtil;
import com.aston.aston_project.util.PasswordUtils;
import com.aston.aston_project.util.UserDetails;
import com.aston.aston_project.util.exception.DuplicateEmailException;
import com.aston.aston_project.util.exception.IncorrectDataException;
import com.aston.aston_project.util.exception.NotFoundDataException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.util.List;

@Service
public class UserService {
    private JwtUtils jwtUtils;
    private UserRepository repository;
    private RoleRepository roleRepository;
    private YandexSearchLocationClient yandexClient;
    private ProductRepository productRepository;
    private ProductDtoMapping productDtoMapping;
    private AddressRepository addressRepository;

    public UserService(JwtUtils jwtUtils,
                       UserRepository repository,
                       RoleRepository roleRepository,
                       @Lazy YandexSearchLocationClient yandexClient,
                       ProductRepository productRepository,
                       ProductDtoMapping productDtoMapping,
                       AddressRepository addressRepository) {
        this.jwtUtils = jwtUtils;
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.yandexClient = yandexClient;
        this.productRepository = productRepository;
        this.productDtoMapping = productDtoMapping;
        this.addressRepository = addressRepository;
    }

    public UserDetails getUserDetailsByEmail(String email) throws NotFoundDataException {
        User user = getUserByEmail(email);
        return UserDetails.builder()
                .email(user.getEmail())
                .role(user.getRole().getName())
                .password(user.getPassword())
                .build();
    }

    public User getUserByEmail(String email) {
        return repository.findUserByEmail(email).orElseThrow(() -> new NotFoundDataException("User not found"));
    }

    public String auth(String email, String password) {
        User user = getUserByEmail(email);
        if (PasswordUtils.compare(user.getPassword(), password)) {
            return jwtUtils.getToken(email);
        }
        throw new NotFoundDataException("User data is incorrect");
    }

    public String signUp(SignUpRequest request) {
        checkSignUpRequest(request);

        User user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(PasswordUtils.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(roleRepository.getRoleUser())
                .build();

        repository.save(user);
        return jwtUtils.getToken(user.getEmail());
    }

    private void checkSignUpRequest(SignUpRequest request) {
        if (!request.getEmail().matches("^(\\S+)@(\\S+)\\.(\\S+)$")) {
            throw new IncorrectDataException("Email %s is incorrect".formatted(request.getEmail()));
        }
        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("User with email = %s already exists".formatted(request.getEmail()));
        }
        if (!request.getPhone().matches("^[87]\\d{10}$")) {
            throw new IncorrectDataException("Phone %s is incorrect".formatted(request.getPhone()));
        }
    }

    public YandexResponse getCoordinates(String location, String address) {
        try {
            LocationUtil.parse(location);
            return yandexClient.getLocation(location, address);
        } catch (NumberFormatException e) {
            throw new IncorrectDataException("Location is incorrect");
        }
    }

    @Transactional
    public void addProductToWishList(String email, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundDataException("Product not found"));

        User user = getUserByEmail(email);

        if (!user.getWishList().contains(product)) {
            user.getWishList().add(product);
        }
    }

    @Transactional
    public void removeProductFromWishList(String email, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundDataException("Product not found"));

        User user = getUserByEmail(email);

        user.getWishList().remove(product);
    }

    public List<ProductDtoShort> getWishList(String email) {
        return getUserByEmail(email).getWishList()
                .stream()
                .map(productDtoMapping::entityToDtoShort)
                .toList();
    }

    @Transactional
    public void addAddress(String email, AddressDto dto) {
        User user = getUserByEmail(email);
        Address address = Address.builder()
                .city(dto.getCity())
                .street(dto.getStreet())
                .house(dto.getHouse())
                .room(dto.getRoom())
                .build();
        if (!user.getAddresses().contains(address)) {
            addressRepository.save(address);
            user.getAddresses().add(address);
        }
    }

    @Transactional
    public void removeAddress(String email, Long addressId) {
        User user = getUserByEmail(email);
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundDataException("Address not found"));

        user.getAddresses().remove(address);
    }
}
