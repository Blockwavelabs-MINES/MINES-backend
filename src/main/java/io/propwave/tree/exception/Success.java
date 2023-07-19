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

    // social
    GET_ACCOUNT_LIST_SUCCESS(HttpStatus.OK, "소셜 계정 전체 조회를 완료했습니다."),
    CONNECT_SUCCESS(HttpStatus.OK, "소셜 계정 연동이 완료됐습니다."),
    DISCONNECT_SUCCESS(HttpStatus.OK, "소셜 계정 연동 해제가 완료됐습니다."),
    REFRESH_SUCCESS(HttpStatus.OK, "소셜 계정 OAuth2.0 토큰 갱신이 완료됐습니다."),
    TWEET_SUCCESS(HttpStatus.CREATED, "트윗 올리기가 완료됐습니다."),

    // user
    GET_USER_INFORMATION_SUCCESS(HttpStatus.OK, "유저 정보 조회가 완료됐습니다."),
    CHECK_USER_ID_SUCCESS(HttpStatus.OK, "유저 아이디 중복 확인이 완료됐습니다."),
    UPDATE_USER_ID_SUCCESS(HttpStatus.OK, "유저 아이디 등록이 완료됐습니다."),
    UPDATE_PROFILE_SUCCESS(HttpStatus.OK, "유저 프로필 업데이트가 완료됐습니다."),
    UPDATE_LANGUAGE_SUCCESS(HttpStatus.OK, "유저 언어 업데이트가 완료됐습니다."),

    // decorate
    GET_PROFILE_DECORATE_SUCCESS(HttpStatus.OK, "프로필 꾸미기 설정 조회가 완료됐습니다."),
    UPDATE_PROFILE_DECORATE_SUCCESS(HttpStatus.OK, "프로필 꾸미기 업데이가 완료됐습니다."),

    // link
    CREATE_LINK_SUCCESS(HttpStatus.CREATED, "링크 생성이 완료됐습니다."),
    GET_LINK_LIST_SUCCESS(HttpStatus.OK, "링크 리스트 조회가 완료됐습니다."),
    UPDATE_LINK_SUCCESS(HttpStatus.OK, "링크 업데이트가 완료됐습니다."),
    DELETE_LINK_SUCCESS(HttpStatus.OK, "링크 삭제가 완료됐습니다,"),

    // wallet
    REGISTER_WALLET_SUCCESS(HttpStatus.CREATED, "지갑 등록이 완료됐습니다."),
    DELETE_WALLET_SUCCESS(HttpStatus.OK, "지갑 삭제가 완료됐습니다."),
    GET_WALLET_LIST_SUCCESS(HttpStatus.OK, "지갑 전체 조회가 완료됐습니다."),

    // send
    GET_SEND_INFORMATION_SUCCESS(HttpStatus.OK, "송금 정보 조회가 완료됐습니다."),
    CREATE_RECEIVE_LINK_SUCCESS(HttpStatus.CREATED, "토큰 수령 링크 생성이 완료됐습니다."),
    UPDATE_SEND_INFORMATION_SUCCESS(HttpStatus.OK, "수령자 정보 업데이트가 완료됐습니다."),
    ;

    private final HttpStatus code;
    private final String message;
}
