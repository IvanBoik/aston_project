package com.aston.aston_project.jwt;

import com.aston.aston_project.util.exception.TokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@AllArgsConstructor
public class JwtUtils {

    private JwtProperties props;

    public String getUserEmail(String token){
        return validateAndExstractUserEmail(token);
    }

    private String validateAndExstractUserEmail(String token) {
        try{
            DecodedJWT decodedToken = getVerifier().verify(token);
            String email = decodedToken.getClaim("email").asString();
            if(email.matches("^(\\S+)@(\\S+)\\.(\\S+)$")) {
                return email;
            }
        }catch (Exception e){
            throw new TokenException("Token parse exception");
        }
        throw new TokenException("User email signature is not valid in token");
    }

    private JWTVerifier getVerifier() {
        return JWT.require(Algorithm.HMAC256(props.getSecret()))
                .withSubject(props.getSubject())
                .withIssuer(props.getIssuer())
                .build();
    }

    private JWTCreator.Builder getSigner(String email){
        return JWT.create()
                .withIssuer(props.getIssuer())
                .withSubject(props.getSubject())
                .withClaim("email", email)
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.DAYS));
    }

    public String getToken(String email) {
        return getSigner(email).sign(Algorithm.HMAC256(props.getSecret()));
    }
}
