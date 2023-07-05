package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.dto.response.SocialLoginResponseService;
import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.auth.domain.SocialUser;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.SocialUserRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.exception.model.ConflictException;
import io.propwave.tree.exception.model.NotFoundException;
import io.propwave.tree.external.client.dto.twitter.TwitterUser;
import io.propwave.tree.external.client.twitter.TwitterService;
import org.springframework.stereotype.Service;

@Service
public class TwitterSocialServiceImpl extends SocialService {

    private final TwitterService twitterService;

    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;

    public TwitterSocialServiceImpl(TwitterService twitterService, UserRepository userRepository, SocialUserRepository socialUserRepository) {
        super(userRepository, socialUserRepository);
        this.twitterService = twitterService;
        this.userRepository = userRepository;
        this.socialUserRepository = socialUserRepository;
    }

    @Override
    public SocialLoginResponseService connect(Long id, String code) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        TwitterUser twitterUser = twitterService.getUserInfo(code);

        if (socialUserRepository.existsBySocialIdAndSocialTypeAndIsConnected(twitterUser.getId(), SocialType.TWITTER, true)) {
            throw new ConflictException("이미 다른 계정과 연동된 소셜 계정입니다.");
        }

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
