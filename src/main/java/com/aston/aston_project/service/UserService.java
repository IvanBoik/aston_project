package com.aston.aston_project.service;


import com.aston.aston_project.dto.SignUpRequest;
import com.aston.aston_project.entity.User;
import com.aston.aston_project.feign.client.YandexSearchLocationClient;
import com.aston.aston_project.feign.dto.YandexResponse;
import com.aston.aston_project.jwt.JwtUtils;
import com.aston.aston_project.repository.RoleRepository;
import com.aston.aston_project.repository.UserRepository;
import com.aston.aston_project.util.LocationUtil;
import com.aston.aston_project.util.PasswordUtils;
import com.aston.aston_project.util.UserDetails;
import com.aston.aston_project.util.exception.DuplicateEmailException;
import com.aston.aston_project.util.exception.IncorrectDataException;
import com.aston.aston_project.util.exception.NotFoundDataException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private JwtUtils jwtUtils;
    private UserRepository repository;
    private RoleRepository roleRepository;
    private YandexSearchLocationClient yandexClient;

    public UserService(JwtUtils jwtUtils, UserRepository repository, RoleRepository roleRepository, @Lazy
    YandexSearchLocationClient yandexClient) {
        this.jwtUtils = jwtUtils;
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.yandexClient = yandexClient;
    }

    public UserDetails getUserDetailsByEmail(String email) throws NotFoundDataException {
        User user = repository.findUserByEmail(email);
        if(user == null){
            throw new NotFoundDataException("User with that email not found");
        }
        return UserDetails.builder()
                .email(user.getEmail())
                .role(user.getRole().getName())
                .password(user.getPassword())
                .build();
    }

    public String auth(String email, String password) {
        User user = repository.findUserByEmail(email);
        if(user!=null){
            if (PasswordUtils.compare(user.getPassword(),password)) {
                return jwtUtils.getToken(email);
            }
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
        if (!request.getPhone().toString().matches("^[87]\\d{10}$")) {
            throw new IncorrectDataException("Phone %s is incorrect".formatted(request.getPhone()));
        }
    }

    public YandexResponse yandexTest(String location, String address) {
        try{
            LocationUtil.parse(location);
            return yandexClient.getLocation(location, address);
        }catch (NumberFormatException e){
            throw new IncorrectDataException("Location is incorrect");
        }
    }
}
