package com.aston.aston_project.api.auth;

import com.aston.aston_project.config.SecurityConfig;
import com.aston.aston_project.controller.AuthController;
import com.aston.aston_project.dto.SignUpRequest;
import com.aston.aston_project.entity.Role;
import com.aston.aston_project.entity.User;
import com.aston.aston_project.entity.en.RoleEnum;
import com.aston.aston_project.filter.AuthFilter;
import com.aston.aston_project.jwt.JwtUtils;
import com.aston.aston_project.repository.RoleRepository;
import com.aston.aston_project.repository.UserRepository;
import com.aston.aston_project.service.UserService;
import com.aston.aston_project.util.CustomAuthEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@WebMvcTest(controllers = {AuthController.class, SecurityConfig.class, AuthFilter.class, CustomAuthEntryPoint.class, UserService.class},
        excludeAutoConfiguration = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserRepository userRepository;

    private User test;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init(){
        Role role = new Role();
        role.setId(1);
        role.setName(RoleEnum.ROLE_USER);
        test = User.builder()
                .email("test@test.tt")
                .role(role)
                .password(Hashing.sha256().hashBytes("1234".getBytes(StandardCharsets.UTF_8)).toString()).build();

        when(userRepository.findUserByEmail("test@test.tt")).thenReturn(test);
    }

    @ParameterizedTest
    @CsvSource("test@test.tt,1234")
    public void return_status_success_when_data_is_correct(String email,String password) throws Exception {
        mockMvc.perform(post("/auth")
                        .queryParam("email",email)
                        .queryParam("password",password))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource("test@test.tt,1234")
    public void return_method_not_allowed_when_data_is_correct(String email,String password) throws Exception {
        mockMvc.perform(get("/auth")
                        .queryParam("email",email)
                        .queryParam("password",password))
                .andExpect(status().isMethodNotAllowed());
    }

    @ParameterizedTest
    @CsvSource("test@test.tt,123")
    public void return_status_bad_request_when_data_is_incorrect(String email,String password) throws Exception {
        mockMvc.perform(post("/auth")
                        .queryParam("email",email)
                        .queryParam("password",password))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = "test@test.tt")
    public void return_status_bad_request_when_query_param_missed(String email) throws Exception {
        mockMvc.perform(post("/auth")
                        .queryParam("email",email))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource("dasdasd,1234")
    public void return_status_bad_request_when_email_is_not_correct(String email,String password) throws Exception {
        mockMvc.perform(post("/auth")
                        .queryParam("email",email)
                        .queryParam("password",password))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = "usermailru")
    public void signup_return_bad_request_when_email_is_not_correct(String email) throws Exception {
        SignUpRequest request = new SignUpRequest(
                "name", "surname", email, "password", "79626211678"
        );
        mockMvc.perform(post("/auth/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = "10000000000000")
    public void signup_return_bad_request_when_phone_is_not_correct(String phone) throws Exception {
        SignUpRequest request = new SignUpRequest(
                "name", "surname", "user.mail.ru", "password", phone
        );
        mockMvc.perform(post("/auth/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signup_return_status_success_when_data_is_correct() throws Exception {
        SignUpRequest request = new SignUpRequest(
                "name", "surname", "user@mail.ru", "password", "79626211678"
        );
        mockMvc.perform(post("/auth/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
