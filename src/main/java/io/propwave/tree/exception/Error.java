package io.propwave.tree.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Error {

    // custom
    BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "인증되지 않은 요청입니다."),
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, "해당 자원에 권한이 없습니다."),
    NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "존재하지 않는 자원입니다."),
    CONFLICT_EXCEPTION(HttpStatus.CONFLICT, "중복되는 자원입니다"),

    // common
    REQUEST_BODY_MISSING_ERROR(HttpStatus.BAD_REQUEST, "요청 바디값이 존재하지 않습니다."),
    MISSING_PARAMETER_ERROR(HttpStatus.BAD_REQUEST, "요청한 값이 존재하지 않습니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "요청 형식이 잘못됐습니다."),
    TYPE_MISSMATCH_ERROR(HttpStatus.BAD_REQUEST, "요청 타입이 잘못됐습니다."),
    METHOD_NOT_ALLOWED_ERROR(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP Method 입니다."),

    // auth
    SOCIAL_LOGIN_ERROR(HttpStatus.UNAUTHORIZED, "인증할 수 없는 OAuth2.0 사용자압니다."),
    TOKEN_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    MALFORMED_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "잘못된 토큰 형식입니다."),
    NULL_TOKE_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    TOKEN_SIGNATURE_EXCEPTION(HttpStatus.UNAUTHORIZED, "변조된 토큰입니다."),
    ;

    private final HttpStatus code;
    private final String message;

}
