package io.propwave.tree.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Error {

    // common
    MISSING_PARAMETER_ERROR(HttpStatus.BAD_REQUEST, "요청한 값이 존재하지 않습니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "요청 형식이 잘못됐습니다."),
    METHOD_NOT_ALLOWED_ERROR(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP Method 입니다."),

    // auth
    SOCIAL_LOGIN_ERROR(HttpStatus.UNAUTHORIZED, "인증할 수 없는 OAuth2.0 사용자압니다."),
    ;

    private final HttpStatus code;
    private final String message;

}
