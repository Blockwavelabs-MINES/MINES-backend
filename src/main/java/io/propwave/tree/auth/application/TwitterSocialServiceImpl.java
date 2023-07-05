package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.dto.response.SocialLoginResponseService;
import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.auth.domain.SocialUser;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.SocialUserRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.exception.model.NotFoundException;
import io.propwave.tree.external.client.dto.twitter.TwitterUser;
import io.propwave.tree.external.client.twitter.TwitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwitterSocialServiceImpl extends SocialService {

    private final TwitterService twitterService;

    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;

    @Override
    public SocialLoginResponseService connect(Long id, String code) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        TwitterUser twitterUser = twitterService.getUserInfo(code);

        socialUserRepository.save(SocialUser.newInstance(
                user,
                twitterUser.getId(),
                SocialType.TWITTER,
                twitterUser.getUsername(),
                twitterUser.getAccessToken(),
                twitterUser.getRefreshToken(),
                true
        ));

        return SocialLoginResponseService.of(twitterUser.getUsername());
    }
}
