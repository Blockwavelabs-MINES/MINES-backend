package io.propwave.tree.auth.application;

import io.propwave.tree.auth.domain.Role;
import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.RefreshTokenRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.config.security.jwt.JwtTokenProvider;
import io.propwave.tree.config.security.model.JwtToken;
import io.propwave.tree.external.client.dto.GoogleUserInfo;
import io.propwave.tree.external.client.google.GoogleService;
import io.propwave.tree.utils.SamTreeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleAuthServiceImpl extends AuthService {

    private final GoogleService googleService;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public User login(String code) {
        GoogleUserInfo googleUserInfo = googleService.getUserInfo(code);

        return userRepository.findBySocialInformationEmail(googleUserInfo.getEmail())
                .orElseGet(() -> userRepository.save(User.newInstance(
                        SamTreeUtil.makeUserIdByEmail(googleUserInfo.getEmail()),
                        SamTreeUtil.makeUserIdByEmail(googleUserInfo.getEmail()) + "'s 3Tree page :)",
                        googleUserInfo.getPicture(),
                        null,
                        googleUserInfo.getId(),
                        googleUserInfo.getEmail(),
                        SocialType.GOOGLE,
                        Role.USER
                )));
    }

    @Override
    public JwtToken getAccessToken(String email, Role role) {
        return jwtTokenProvider.generateToken(email, role);
    }
}
