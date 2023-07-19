package io.propwave.tree.auth.application.util;

import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.auth.domain.SocialUser;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.SocialUserRepository;
import io.propwave.tree.exception.model.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialServiceUtil {

    public static SocialUser findSocialUserByUserAndSocialType(SocialUserRepository socialUserRepository, User user, SocialType socialType) {
        return socialUserRepository.findByUserAndSocialType(user, socialType)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 소셜 계정입니다."));
    }

    public static SocialUser findSocialUserByUserAndUsername(SocialUserRepository socialUserRepository, User user, String username) {
        return socialUserRepository.findByUserAndUsername(user, username)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 소셜 유저입니다."));
    }

    public static SocialUser findSocialUserByUserId(SocialUserRepository socialUserRepository, Long id) {
        return socialUserRepository.findByUserId(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
    }
}
