package io.propwave.tree.auth.ui;

import io.propwave.tree.auth.application.AuthService;
import io.propwave.tree.auth.application.AuthServiceProvider;
import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.config.security.model.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceProvider authServiceProvider;

    @GetMapping("/auth/login")
    public JwtToken login(@RequestParam("code") String code) {
        AuthService authService = authServiceProvider.getAuthService(SocialType.GOOGLE);
        User user = authService.login(code);
        return authService.getAccessToken(user.getSocialInformation().getEmail(), user.getRole());
    }
}
