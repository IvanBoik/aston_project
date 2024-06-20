package com.aston.aston_project.config;

import com.aston.aston_project.filter.AuthFilter;
import com.aston.aston_project.filter.ExceptionHandlerFilter;
import com.aston.aston_project.util.CustomAuthEntryPoint;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;


/**
 * Spring security configuration
 * @author Kirill Zemlyakov
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebMvcConfigurationSupport {

    private AuthFilter authFilter;
    private ExceptionHandlerFilter exceptionFilter;
    private CustomAuthEntryPoint authEntryPoint;

    /**
     * General security configuration bean
     * 'exceptionHandling' allows to handle {@link org.springframework.security.core.AuthenticationException} in {@link CustomAuthEntryPoint}
     * 'authorizeHttpRequests' allows to define restriction to various paths
     * @author Kirill Zemlyakov
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .passwordManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(Customizer.withDefaults())
                .exceptionHandling(configurer->
                        configurer.authenticationEntryPoint(authEntryPoint))
                .authorizeHttpRequests(reqMatch ->
                        reqMatch.requestMatchers("auth/test").authenticated()
                                .anyRequest().permitAll())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionFilter, AuthFilter.class)
                .build();
    }

    /**
     * When response body wrapping into JSON this bean allows to correct {@link String} mapping
     * @author Kirill Zemlyakov
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
        super.addDefaultHttpMessageConverters(converters);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
