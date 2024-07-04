package com.aston.aston_project.config;

import com.aston.aston_project.filter.AuthFilter;
import com.aston.aston_project.filter.ExceptionHandlerFilter;
import com.aston.aston_project.util.CustomAuthEntryPoint;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;


/**
 * Spring security configuration
 *
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
     *
     * @author Kirill Zemlyakov
     */
    @Bean
    @ConditionalOnProperty(name = "security", havingValue = "enabled")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .passwordManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .exceptionHandling(configurer ->
                        configurer.authenticationEntryPoint(authEntryPoint))
                .authorizeHttpRequests(reqMatch ->
                        reqMatch.requestMatchers("/api/v1/managers/**").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/products/attributes/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/products/attributes/values/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/products/types/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/v1/producers/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/producers/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/producers/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/countries/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/**").permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionFilter, AuthFilter.class)
                .build();
    }

    /**
     * When security is disabled
     * this filterChain is using
     *
     * @author Kirill Zemlyakov
     */
    @Bean
    @ConditionalOnProperty(name = "security", havingValue = "disabled", matchIfMissing = true)
    public SecurityFilterChain noSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .passwordManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .exceptionHandling(configurer ->
                        configurer.authenticationEntryPoint(authEntryPoint))
                .authorizeHttpRequests(registry ->
                        registry.anyRequest().permitAll()
                )
                .build();
    }

    /**
     * Method configures ignore accept headers
     *
     * @param configurer configuring default media type to application/json
     */
    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * When response body wrapping into JSON this bean allows to correct {@link String} mapping
     *
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


    /**
     * Configuring project message converters
     *
     * @param converters - autowired by spring value
     * @author Kirill Zemlyakov
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
        super.addDefaultHttpMessageConverters(converters);
    }
}