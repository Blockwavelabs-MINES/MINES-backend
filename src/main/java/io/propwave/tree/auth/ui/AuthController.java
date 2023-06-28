package io.propwave.tree.auth.ui;

import io.propwave.tree.auth.application.AuthService;
import io.propwave.tree.auth.application.AuthServiceProvider;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.ui.dto.request.LoginRequest;
import io.propwave.tree.auth.ui.dto.response.LoginResponse;
import io.propwave.tree.common.dto.ApiResponse;
import io.propwave.tree.exception.Success;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceProvider authServiceProvider;

    @PostMapping("/auth/login")
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
}
