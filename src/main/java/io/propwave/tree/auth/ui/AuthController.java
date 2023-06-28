package io.propwave.tree.auth.ui;

import io.propwave.tree.auth.application.AuthService;
import io.propwave.tree.auth.application.AuthServiceProvider;
import io.propwave.tree.auth.application.JwtService;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.ui.dto.request.LoginRequest;
import io.propwave.tree.auth.ui.dto.response.LoginResponse;
import io.propwave.tree.common.dto.ApiResponse;
import io.propwave.tree.config.security.jwt.JwtTokenProvider;
import io.propwave.tree.config.security.model.JwtToken;
import io.propwave.tree.exception.Success;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceProvider authServiceProvider;

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestParam("code") final String code, @RequestBody @Valid final LoginRequest request) {
        AuthService authService = authServiceProvider.getAuthService(request.getSocialType());
        User user = authService.login(code);

        return new ResponseEntity<>(
                ApiResponse.success(Success.LOGIN_SUCCESS,
                        LoginResponse.of(
                                authService.getAccessToken(user.getSocialInformation().getEmail(), user.getRole()),
                                user.getProfile().getProfileName() == null
                        )
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse<JwtToken>> refresh(@RequestHeader("refresh_token") final String refreshToken) {
        return new ResponseEntity<>(
                ApiResponse.success(Success.TOKEN_REFRESH_SUCCESS, jwtService.getAccessTokenByRefreshToken(refreshToken)),
                HttpStatus.OK
        );
    }
}
