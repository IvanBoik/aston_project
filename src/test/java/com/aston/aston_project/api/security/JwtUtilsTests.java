package com.aston.aston_project.api.security;

import com.aston.aston_project.jwt.JwtProperties;
import com.aston.aston_project.jwt.JwtUtils;
import com.aston.aston_project.util.exception.TokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({MockitoExtension.class})
public class JwtUtilsTests {

    @Spy
    private JwtProperties properties;

    @InjectMocks
    private JwtUtils jwtUtils;

    @BeforeEach
    public void init(){
        properties.setSecret("test");
        properties.setIssuer("test");
        properties.setSubject("test");
    }

    @ParameterizedTest
    @ValueSource(strings = "test@test.tt")
    public void jwt_utils_get_correct_email(String email){
        String token = jwtUtils.getToken(email);
        String userEmail = jwtUtils.getUserEmail(token);
        assertThat(userEmail).isEqualTo("test@test.tt");
    }
    @ParameterizedTest
    @ValueSource(strings = "some string")
    public void jwt_utis_throws_token_parse_exception_when_token_is_malformed(String token){
        assertThrows(TokenException.class, ()->jwtUtils.getUserEmail(token));
    }

    @Test
    public void jwt_utils_throws_token_exception_when_email_is_not_present(){
        assertThrows(TokenException.class, ()->jwtUtils.getUserEmail(null));
    }

    @Test
    public void jwt_utils_throws_token_exception_when_email_is_empty(){
        assertThrows(TokenException.class, ()->jwtUtils.getUserEmail(""));
    }

    @Test
    public void jwt_utils_throws_token_exception_when_email_is_blank(){
        assertThrows(TokenException.class, ()->jwtUtils.getUserEmail(" "));
    }

    @Test
    public void jwt_utils_throws_token_parse_exception_when_email_pattern_is_not_valid(){
        String invalidToken = jwtUtils.getToken("test");
        assertThrows(TokenException.class, ()->jwtUtils.getUserEmail(invalidToken));
    }

    @ParameterizedTest
    @ValueSource(strings = "test@test.tt")
    public void jwt_utils_throws_token_parse_exception_when_token_is_expired(String email){
        String invalidToken = JWT.create()
                .withClaim("email", email)
                .withExpiresAt(Instant.now().minus(1, ChronoUnit.DAYS))
                .withIssuer("test")
                .withSubject("test")
                .sign(Algorithm.HMAC256("test"));
        assertThrows(TokenException.class,()-> jwtUtils.getUserEmail(invalidToken));
    }

    @ParameterizedTest
    @ValueSource(strings = "test@test.tt")
    public void jwt_utils_throws_token_parse_exception_when_secret_is_incorrect(String email){
        String invalidToken = JWT.create()
                .withClaim("email", email)
                .withExpiresAt(Instant.now().minus(1, ChronoUnit.DAYS))
                .withIssuer("test")
                .withSubject("test")
                .sign(Algorithm.HMAC256((String) "incorrect"));

        assertThrows(TokenException.class,()-> jwtUtils.getUserEmail(invalidToken));
    }

    @ParameterizedTest
    @ValueSource(strings = "test@test.tt")
    public void jwt_utils_throws_token_parse_exception_when_issuer_is_incorrect(String email){
        String invalidToken = JWT.create()
                .withClaim("email", email)
                .withExpiresAt(Instant.now().minus(1, ChronoUnit.DAYS))
                .withIssuer("incorrect")
                .withSubject("test")
                .sign(Algorithm.HMAC256((String) "test"));

        assertThrows(TokenException.class,()-> jwtUtils.getUserEmail(invalidToken));
    }

    @ParameterizedTest
    @ValueSource(strings = "test@test.tt")
    public void jwt_utils_throws_token_parse_exception_when_subject_is_incorrect(String email){
        String invalidToken = JWT.create()
                .withClaim("email", email)
                .withExpiresAt(Instant.now().minus(1, ChronoUnit.DAYS))
                .withIssuer("test")
                .withSubject("incorrect")
                .sign(Algorithm.HMAC256((String) "test"));

        assertThrows(TokenException.class,()-> jwtUtils.getUserEmail(invalidToken));
    }
}
