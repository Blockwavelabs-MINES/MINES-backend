package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.dto.response.LoginResponseService;
import io.propwave.tree.auth.domain.ProfileDecorate;
import io.propwave.tree.auth.domain.Role;
import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.ProfileDecorateRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.config.security.jwt.JwtTokenProvider;
import io.propwave.tree.config.security.model.JwtToken;
import io.propwave.tree.external.client.dto.google.GoogleUserInfo;
import io.propwave.tree.external.client.google.GoogleService;
import io.propwave.tree.utils.SamTreeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GoogleAuthServiceImpl extends AuthService {

    private final GoogleService googleService;

    private final UserRepository userRepository;
    private final ProfileDecorateRepository profileDecorateRepository;

    @Override
    @Transactional
    public LoginResponseService login(String code) {
        GoogleUserInfo googleUserInfo = googleService.getUserInfo(code);
        boolean isSignup = !userRepository.existsBySocialInformationEmail(googleUserInfo.getEmail());

        User user = userRepository.findBySocialInformationEmail(googleUserInfo.getEmail())
                .orElseGet(() -> userRepository.save(User.newInstance(
                        SamTreeUtil.makeUserIdByEmail(googleUserInfo.getEmail()),
                        SamTreeUtil.makeUserIdByEmail(googleUserInfo.getEmail()) + "'s 3Tree page :)",
                        googleUserInfo.getPicture(),
                        googleUserInfo.getName(),
                        googleUserInfo.getId(),
                        googleUserInfo.getEmail(),
                        SocialType.GOOGLE,
                        Role.USER
                )));

        if (isSignup) {
            profileDecorateRepository.save(ProfileDecorate.newInstance(user));
        }

        return LoginResponseService.of(
                user,
                isSignup
        );
    }
}
