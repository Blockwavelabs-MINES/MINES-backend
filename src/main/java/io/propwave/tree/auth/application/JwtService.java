package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.util.UserServiceUtil;
import io.propwave.tree.auth.domain.RefreshToken;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.RefreshTokenRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.config.security.jwt.JwtTokenProvider;
import io.propwave.tree.config.security.model.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public JwtToken getAccessTokenByRefreshToken(String refreshToken) {
        RefreshToken token = RefreshTokenServiceUtil.findRefreshTokenByTokenString(refreshTokenRepository, refreshToken);
        User user = UserServiceUtil.findUserUserByEmail(userRepository, token.getEmail());

        // 기존의 refresh token 삭제
        refreshTokenRepository.deleteById(refreshToken);

        return jwtTokenProvider.generateToken(user.getSocialInformation().getEmail(), user.getRole());
    }
}
