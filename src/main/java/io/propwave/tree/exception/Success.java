package io.propwave.tree.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Success {

    // auth
    LOGIN_SUCCESS(HttpStatus.OK, "로그인이 완료됐습니다."),
    TOKEN_REFRESH_SUCCESS(HttpStatus.OK, "토큰 재발급이 완료됐습니다."),

    // user
    GET_USER_INFORMATION_SUCCESS(HttpStatus.OK, "유저 정보 조회가 완료됐습니다."),
    CHECK_USER_ID_SUCCESS(HttpStatus.OK, "유저 아이디 중복 확인이 완료됐습니다."),
    UPDATE_USER_ID_SUCCESS(HttpStatus.OK, "유저 아이디 등록이 완료됐습니다."),
    UPDATE_PROFILE_SUCCESS(HttpStatus.OK, "유저 프로필 업데이트가 완료됐습니다."),
    UPDATE_LANGUAGE_SUCCESS(HttpStatus.OK, "유저 언어 업데이트가 완료됐습니다."),

    // link
    CREATE_LINK_SUCCESS(HttpStatus.CREATED, "링크 생성이 완료됐습니다."),
    UPDATE_LINK_SUCCESS(HttpStatus.OK, "링크 업데이트가 완료됐습니다."),
    DELETE_LINK_SUCCESS(HttpStatus.OK, "링크 삭제가 완료됐습니다,"),
    ;

    private final HttpStatus code;
    private final String message;
}
