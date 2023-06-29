package io.propwave.tree.auth.application;

import io.propwave.tree.auth.domain.RefreshToken;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.RefreshTokenRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.config.security.jwt.JwtTokenProvider;
import io.propwave.tree.config.security.model.JwtToken;
import io.propwave.tree.exception.model.BadRequestException;
import io.propwave.tree.exception.model.NotFoundException;
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
        RefreshToken token = refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new BadRequestException("재 로그인이 필요한 사용자입니다."));
        User user = userRepository.findBySocialInformationEmail(token.getEmail())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        // 기존의 refresh token 삭제
        refreshTokenRepository.deleteById(refreshToken);

        return jwtTokenProvider.generateToken(user.getSocialInformation().getEmail(), user.getRole());
    }
}
