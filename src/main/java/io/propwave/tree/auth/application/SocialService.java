package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.dto.response.SocialLoginResponseService;
import io.propwave.tree.auth.application.util.SocialServiceUtil;
import io.propwave.tree.auth.application.util.UserServiceUtil;
import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.auth.domain.SocialUser;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.SocialUserRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.auth.ui.dto.response.SocialAccountListResponse;
import io.propwave.tree.external.client.dto.OAuth2Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public abstract class SocialService {

    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;

    public abstract SocialLoginResponseService connect(Long id, String code);
    public abstract OAuth2Token refresh(Long id, SocialType socialType);

    @Transactional
    public List<SocialAccountListResponse> getSocialAccountList(Long id) {

        User user = UserServiceUtil.findUserById(userRepository, id);
        List<SocialUser> socialUserList = socialUserRepository.findAllByUser(user);

        return socialUserList.stream()
                .map(socialUser -> SocialAccountListResponse.of(socialUser.getSocialType(), socialUser.getUsername()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void disconnect(Long id, SocialType socialType) {

        User user = UserServiceUtil.findUserById(userRepository, id);
        SocialUser socialUser = SocialServiceUtil.findSocialUserByUserAndSocialType(socialUserRepository, user, socialType);

        socialUserRepository.delete(socialUser);
    }
}
