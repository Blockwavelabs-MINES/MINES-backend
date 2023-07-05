package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.dto.response.SocialLoginResponseService;
import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.auth.domain.SocialUser;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.SocialUserRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.exception.model.NotFoundException;
import io.propwave.tree.external.client.dto.OAuth2Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public abstract class SocialService {

    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;

    public abstract SocialLoginResponseService connect(Long id, String code);
    public abstract OAuth2Token refresh(Long id, SocialType socialType);

    @Transactional
    public void disconnect(Long id, SocialType socialType) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        SocialUser socialUser = socialUserRepository.findByUserAndSocialType(user, socialType)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 소셜 계정입니다."));

        socialUserRepository.delete(socialUser);
    }
}
