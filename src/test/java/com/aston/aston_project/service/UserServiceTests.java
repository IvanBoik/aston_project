package com.aston.aston_project.service;

import com.aston.aston_project.config.TestConfig;
import com.aston.aston_project.dto.SignUpRequest;
import com.aston.aston_project.entity.Role;
import com.aston.aston_project.entity.en.RoleEnum;
import com.aston.aston_project.jwt.JwtUtils;
import com.aston.aston_project.repository.RoleRepository;
import com.aston.aston_project.repository.UserRepository;
import com.aston.aston_project.util.exception.DuplicateEmailException;
import com.aston.aston_project.util.exception.IncorrectDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfig.class)
public class UserServiceTests {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void init() {
        when(userRepository.existsByEmail("existing@mail.ru")).thenReturn(true);
        when(userRepository.existsByEmail("user@mail.ru")).thenReturn(false);
        when(jwtUtils.getToken("user@mail.ru")).thenReturn("token");

        Role role = new Role();
        role.setId(1);
        role.setName(RoleEnum.ROLE_USER);

        when(roleRepository.getRoleUser()).thenReturn(role);
    }

    @Test
    public void sign_up_throws_duplicate_email_exception() {
        SignUpRequest request = new SignUpRequest(
                "name", "surname", "existing@mail.ru", "password", "79626211678"
        );

        assertThrows(DuplicateEmailException.class, () -> userService.signUp(request));
    }

    @Test
    public void sign_up_throws_invalid_email_exception() {
        SignUpRequest request = new SignUpRequest(
                "name", "surname", "usermailru", "password", "79626211678"
        );

        assertThrows(IncorrectDataException.class, () -> userService.signUp(request));
    }

    @Test
    public void sign_up_throws_invalid_phone_exception() {
        SignUpRequest request = new SignUpRequest(
                "name", "surname", "user@mail.ru", "password", "10000000000"
        );

        assertThrows(IncorrectDataException.class, () -> userService.signUp(request));
    }

    @Test
    public void sign_up_full_works() {
        SignUpRequest request = new SignUpRequest(
                "name", "surname", "user@mail.ru", "password", "79626211678"
        );

        assertNotNull(userService.signUp(request));
    }
}
