package com.aston.aston_project.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Project validation configuration
 * @author K. Zemlyakov
 */
@Configuration
public class ValidationConfig {

    /**
     * Methods describes bean which will handle method param constraints
     * @return {@link org.springframework.beans.factory.config.BeanPostProcessor} which will handle method param constraints
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        return new MethodValidationPostProcessor();
    }
}
