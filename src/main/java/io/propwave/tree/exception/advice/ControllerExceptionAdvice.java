package io.propwave.tree.exception.advice;

import feign.FeignException;
import io.propwave.tree.common.dto.ApiResponse;
import io.propwave.tree.exception.Error;
import io.propwave.tree.exception.model.SamTreeException;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

    /**
     * 400
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(final HttpMessageNotReadableException error) {
        Sentry.captureException(error);
        log.error(String.format("ERROR TYPE: %s%nERROR CONTENT: %s", error.toString(), error.getMessage()));
        return new ResponseEntity<>(
                ApiResponse.error(Error.JSON_PARSING_ERROR),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ApiResponse> handleMissingServletRequestParameterException(final MissingServletRequestParameterException error) {
        Sentry.captureException(error);
        log.error(String.format("ERROR TYPE: %s%nERROR CONTENT: %s", error.toString(), error.getMessage()));
        return new ResponseEntity<>(
                ApiResponse.error(Error.MISSING_PARAMETER_ERROR),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException error) {
        Sentry.captureException(error);
        log.error(String.format("ERROR TYPE: %s%nERROR CONTENT: %s", error.toString(), error.getMessage()));
        return new ResponseEntity<>(
                ApiResponse.error(Error.VALIDATION_ERROR),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ApiResponse> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException error) {
        Sentry.captureException(error);
        log.error(String.format("ERROR TYPE: %s%nERROR CONTENT: %s", error.toString(), error.getMessage()));
        return new ResponseEntity<>(
                ApiResponse.error(Error.TYPE_MISSMATCH_ERROR),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(FeignException.BadRequest.class)
    protected ResponseEntity<ApiResponse> handleFeignExceptionBadRequest(final FeignException error) {
        Sentry.captureException(error);
        log.error(String.format("ERROR TYPE: %s%nERROR CONTENT: %s", error.toString(), error.getMessage()));
        return new ResponseEntity<>(
                ApiResponse.error(Error.SOCIAL_REQUEST_ERROR),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * 401
     */
    @ExceptionHandler(FeignException.Unauthorized.class)
    protected ResponseEntity<ApiResponse> handleFeignExceptionUnauthorized(final FeignException error) {
        Sentry.captureException(error);
        log.error(String.format("ERROR TYPE: %s%nERROR CONTENT: %s", error.toString(), error.getMessage()));
        return new ResponseEntity<>(
                ApiResponse.error(Error.SOCIAL_LOGIN_ERROR),
                HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * 404
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ApiResponse> handleNoHandlerFoundException (final NoHandlerFoundException error) {
        Sentry.captureException(error);
        log.error(String.format("ERROR TYPE: %s%nERROR CONTENT: %s", error.toString(), error.getMessage()));
        return new ResponseEntity<>(
                ApiResponse.error(Error.NOT_FOUND_EXCEPTION),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * 405
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException error) {
        Sentry.captureException(error);
        log.error(String.format("ERROR TYPE: %s%nERROR CONTENT: %s", error.toString(), error.getMessage()));
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
        Sentry.captureException(error);
        log.error(String.format("ERROR TYPE: %s%nERROR CONTENT: %s", error.getError().toString(), error.getMessage()));
        return new ResponseEntity<>(
                ApiResponse.error(error.getError(), error.getMessage()),
                error.getError().getCode()
        );
    }
}
