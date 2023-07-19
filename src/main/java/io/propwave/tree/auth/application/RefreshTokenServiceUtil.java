package io.propwave.tree.auth.application;

import io.propwave.tree.auth.domain.RefreshToken;
import io.propwave.tree.auth.infrastructure.RefreshTokenRepository;
import io.propwave.tree.exception.model.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenServiceUtil {

    public static RefreshToken findRefreshTokenByTokenString(RefreshTokenRepository refreshTokenRepository, String refreshToken) {
        return refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new NotFoundException("재 로그인이 필요한 사용자입니다."));
    }
}
