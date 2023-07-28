package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.dto.response.LoginResponseService;

public abstract class AuthService {

    public abstract LoginResponseService login(String code);

    public void logout(Long userId) {
        // TODO: 추후 로그아웃 구현
    }

}
