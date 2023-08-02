package io.propwave.tree.auth.ui;

import io.propwave.tree.auth.application.AuthService;
import io.propwave.tree.auth.application.AuthServiceProvider;
import io.propwave.tree.auth.application.JwtService;
import io.propwave.tree.auth.application.dto.response.LoginResponseService;
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
public class AuthController {

    private final AuthServiceProvider authServiceProvider;
    private final JwtTokenProvider jwtTokenProvider;

    private final JwtService jwtService;

    @PostMapping("/public/auth/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestParam("code") final String code, @RequestBody @Valid final LoginRequest request) {
        AuthService authService = authServiceProvider.getAuthService(request.getSocialType());
        LoginResponseService loginResponse = authService.login(code);

        return new ResponseEntity<>(
                ApiResponse.success(Success.LOGIN_SUCCESS,
                        LoginResponse.of(
                                jwtTokenProvider.generateToken(loginResponse.getUser().getSocialInformation().getEmail(), loginResponse.getUser().getRole()),
                                loginResponse.getIsSignup()
                        )
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/public/auth/refresh")
    public ResponseEntity<ApiResponse<JwtToken>> refresh(@RequestHeader("Refresh-Token") final String refreshToken) {
        return new ResponseEntity<>(
                ApiResponse.success(Success.TOKEN_REFRESH_SUCCESS, jwtService.getAccessTokenByRefreshToken(refreshToken.substring(7))),
                HttpStatus.OK
        );
    }
}
