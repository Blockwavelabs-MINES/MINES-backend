package io.propwave.tree.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Success {

    LOGIN_SUCCESS(HttpStatus.OK, "로그인이 완료됐습니다."),
    ;

    private final HttpStatus code;
    private final String message;
}
