package com.aston.aston_project.service;


import com.aston.aston_project.entity.User;
import com.aston.aston_project.jwt.JwtUtils;
import com.aston.aston_project.repository.UserRepository;
import com.aston.aston_project.util.PasswordUtils;
import com.aston.aston_project.util.UserDetails;
import com.aston.aston_project.util.exception.NotFoundDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private JwtUtils jwtUtils;
    private UserRepository repository;

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
}
