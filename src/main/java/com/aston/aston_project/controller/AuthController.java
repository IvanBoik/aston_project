package com.aston.aston_project.controller;

import com.aston.aston_project.dto.SignUpRequest;
import com.aston.aston_project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @PostMapping
    public String auth(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password
    ){
        return userService.auth(email,password);
    }

    @PostMapping("/signUp")
    public String signUp(@RequestBody SignUpRequest request) {
        return userService.signUp(request);
    }

    @GetMapping("/test")
    public String test(){
        return "Test";
    }
}
