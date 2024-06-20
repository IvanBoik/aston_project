package com.aston.aston_project.service;

import com.aston.aston_project.dto.SignUpRequest;
import com.aston.aston_project.entity.Role;
import com.aston.aston_project.entity.RoleValue;
import com.aston.aston_project.jwt.JwtUtils;
import com.aston.aston_project.repository.RoleRepository;
import com.aston.aston_project.repository.UserRepository;
import com.aston.aston_project.util.exception.DuplicateEmailException;
import com.aston.aston_project.util.exception.IncorrectDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private RoleRepository roleRepository;

    private UserService userService;

    @BeforeEach
    private void init() {
        when(userRepository.existsByEmail("existing@mail.ru")).thenReturn(true);
        when(userRepository.existsByEmail("user@mail.ru")).thenReturn(false);
        when(jwtUtils.getToken("user@mail.ru")).thenReturn("token");

        Role role = new Role();
        role.setId(1L);
        role.setName(RoleValue.ROLE_USER);

        when(roleRepository.getRoleUser()).thenReturn(role);

        userService = new UserService(jwtUtils, userRepository, roleRepository);
    }

    @Test
    public void sign_up_throws_duplicate_email_exception() {
        SignUpRequest request = new SignUpRequest(
                "name", "surname", "existing@mail.ru", "password", 79626211678L
        );

        assertThrows(DuplicateEmailException.class, () -> userService.signUp(request));
    }

    @Test
    public void sign_up_throws_invalid_email_exception() {
        SignUpRequest request = new SignUpRequest(
                "name", "surname", "usermailru", "password", 79626211678L
        );

        assertThrows(IncorrectDataException.class, () -> userService.signUp(request));
    }

    @Test
    public void sign_up_throws_invalid_phone_exception() {
        SignUpRequest request = new SignUpRequest(
                "name", "surname", "user@mail.ru", "password", 10000000000L
        );

        assertThrows(IncorrectDataException.class, () -> userService.signUp(request));
    }

    @Test
    public void sign_up_full_works() {
        SignUpRequest request = new SignUpRequest(
                "name", "surname", "user@mail.ru", "password", 79626211678L
        );

        assertNotNull(userService.signUp(request));
    }
}
