package com.aston.aston_project.filter;

import com.aston.aston_project.jwt.JwtUtils;
import com.aston.aston_project.service.UserService;
import com.aston.aston_project.util.exception.NotFoundDataException;
import com.aston.aston_project.util.exception.TokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


/**
 * Custom authorization filter which responsibility is to manage Authorization header,
 * validate JWT token and authorize {@link com.aston.aston_project.entity.User}
 * @author Kirill Zemlyakov
 */
@Component
public class AuthFilter extends GenericFilterBean {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String jwt = parseJwt((HttpServletRequest) request);
        if (jwt != null) {
            String email = jwtUtils.getUserEmail(jwt);
            try{
                UserDetails userDetails = userService.getUserDetailsByEmail(email);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }catch(NotFoundDataException e){
                throw new TokenException("User with that email not found exception");
            }
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            return null;
        }
        try {
            return authorization.substring(7);
        } catch (IndexOutOfBoundsException e) {
            throw new TokenException("Authorization header signature is not correct");
        }
    }
}