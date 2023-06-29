package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.dto.response.LoginResponseService;
import io.propwave.tree.auth.domain.Role;
import io.propwave.tree.config.security.model.JwtToken;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AuthService {

    public abstract LoginResponseService login(String code);
    public abstract JwtToken getAccessToken(String principal, Role role);

    public void logout(Long userId) {
        // TODO: 추후 로그아웃 구현
    }

}
