package com.aston.aston_project.controller;

import com.aston.aston_project.dto.SignUpRequest;
import com.aston.aston_project.feign.dto.YandexResponse;
import com.aston.aston_project.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.aston.aston_project.AstonProjectApplication.log;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Validated
public class AuthController {

    private UserService userService;

    @PostMapping
    public String auth(
            @Email
            @NotBlank
            @RequestParam(value = "email") String email,

            @NotBlank
            @RequestParam(value = "password") String password) {

        return userService.auth(email, password);
    }

    @PostMapping("/signUp")
    public String signUp(@RequestBody SignUpRequest request) {
        log.info("Post request /api/v1/auth/signUp/{}", request.toString());
        return userService.signUp(request);
    }
}
