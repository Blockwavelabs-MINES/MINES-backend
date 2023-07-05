package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.dto.response.SocialLoginResponseService;
import io.propwave.tree.auth.domain.SocialType;

public abstract class SocialService {

    public abstract SocialLoginResponseService connect(Long id, String code);

    public void disconnect(Long userId, SocialType socialType) {
        // TODO: 소셜 로그인 연동 해제 구현
    }
}
