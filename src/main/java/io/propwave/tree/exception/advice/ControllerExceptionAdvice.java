package io.propwave.tree.exception.advice;

import feign.FeignException;
import io.jsonwebtoken.ExpiredJwtException;
import io.propwave.tree.common.dto.ApiResponse;
import io.propwave.tree.exception.Error;
import io.propwave.tree.exception.model.SamTreeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * 400
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ApiResponse> handleMissingServletRequestParameterException(final MissingServletRequestParameterException error) {
        return new ResponseEntity<>(
                ApiResponse.error(Error.MISSING_PARAMETER_ERROR),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException error) {
        return new ResponseEntity<>(
                ApiResponse.error(Error.VALIDATION_ERROR),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * 401
     */
    @ExceptionHandler(FeignException.class)
    protected ResponseEntity<ApiResponse> handleFeignException(final FeignException error) {
        return new ResponseEntity<>(
                ApiResponse.error(Error.SOCIAL_LOGIN_ERROR),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ApiResponse> handleExpiredJwtException(final ExpiredJwtException error) {
        return new ResponseEntity<>(
                ApiResponse.error(Error.TOKEN_EXPIRED_ERROR),
                HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * 405
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResponse> HttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException error) {
        return new ResponseEntity<>(
                ApiResponse.error(Error.METHOD_NOT_ALLOWED_ERROR),
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    /**
     * 3Tree custom exception
     */
    @ExceptionHandler(SamTreeException.class)
    protected ResponseEntity<ApiResponse> handleSamTreeException(final SamTreeException error) {
        return new ResponseEntity<>(
                ApiResponse.error(error.getError(), error.getMessage()),
                error.getError().getCode()
        );
    }
}
