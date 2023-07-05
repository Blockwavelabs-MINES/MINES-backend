package io.propwave.tree.auth.application;

import io.propwave.tree.auth.domain.SocialType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class SocialServiceProvider {

    private static final Map<SocialType, SocialService> socialServiceMap = new HashMap<>();

    private final TwitterSocialServiceImpl twitterSocialService;

    @PostConstruct
    void initializeAuthServiceMap() {
        socialServiceMap.put(SocialType.TWITTER, twitterSocialService);
    }

    public SocialService getSocialService(SocialType socialType) {
        return socialServiceMap.get(socialType);
    }
}
