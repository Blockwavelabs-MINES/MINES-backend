package io.propwave.tree.auth.application;

import io.propwave.tree.auth.domain.SocialType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class AuthServiceProvider {

    private static final Map<SocialType, AuthService> authServiceMap = new HashMap<>();

    private final GoogleAuthServiceImpl googleAuthService;

    @PostConstruct
    void initializeAuthServiceMap() {
        authServiceMap.put(SocialType.GOOGLE, googleAuthService);
    }

    public AuthService getAuthService(SocialType socialType) {
        return authServiceMap.get(socialType);
    }
}
