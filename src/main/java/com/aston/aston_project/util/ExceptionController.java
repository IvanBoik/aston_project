package com.aston.aston_project.util;

import com.aston.aston_project.util.exception.DataException;
import com.aston.aston_project.util.exception.DuplicateEmailException;
import com.aston.aston_project.util.exception.TokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * Application exception handling managed here
 * Application ANY response wrapping here see 'beforeBodyWrite' method
 * @author Kirill Zemlyakov
 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
 * @see ResponseWrapper
 * @see CustomAuthEntryPoint
 */
@RestControllerAdvice
@Slf4j
public class ExceptionController implements ResponseBodyAdvice<Object> {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public String authException() {
        return "Access denied";
    }

    @ExceptionHandler({TokenException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public String tokenException(TokenException e){
        return e.getMessage();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public String methodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        return "Method '" +
               ex.getMethod() +
               "' not allowed to this path";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String pageNotFoundException(NoHandlerFoundException ex) {
        return "Path '" +
               ex.getRequestURL() +
               "' not found";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String requestMissed(MissingServletRequestParameterException ex, WebRequest request) {
        return "Missed " +
               ex.getParameterType() +
               " request parameter " +
               ex.getParameterName();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String methodMismatched(MethodArgumentTypeMismatchException ex) {
        return "Argument " +
               ex.getName() +
               " request type " +
               ex.getParameter().getParameterType().getSimpleName();
    }

    @ExceptionHandler(DataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String dataException(DataException e){
        return e.getMessage();
    }


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerError(Throwable thr){
        log.error(thr.getMessage());
        return "Internal server error";
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String duplicateEmailException(DuplicateEmailException e) {
        return e.getMessage();
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ServletServerHttpResponse servletResponse = (ServletServerHttpResponse) response;
        int status = servletResponse.getServletResponse().getStatus();
        return new ResponseWrapper(status,body);
    }
}
