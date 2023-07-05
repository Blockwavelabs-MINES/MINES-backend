package io.propwave.tree.auth.ui;

import io.propwave.tree.auth.application.SocialService;
import io.propwave.tree.auth.application.SocialServiceProvider;
import io.propwave.tree.auth.application.dto.response.SocialLoginResponseService;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.ui.dto.request.DisconnectRequest;
import io.propwave.tree.auth.ui.dto.request.LoginRequest;
import io.propwave.tree.auth.ui.dto.request.RefreshRequest;
import io.propwave.tree.common.dto.ApiResponse;
import io.propwave.tree.exception.Success;
import io.propwave.tree.external.client.dto.OAuth2Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/social")
public class SocialController {

    private final SocialService socialService;
    private final SocialServiceProvider socialServiceProvider;

    @PostMapping("connect")
    public ResponseEntity<ApiResponse<SocialLoginResponseService>> connect(
            @AuthenticationPrincipal User user,
            @RequestParam("code") final String code,
            @RequestBody final LoginRequest request
    ) {
        SocialService socialService = socialServiceProvider.getSocialService(request.getSocialType());
        return new ResponseEntity<>(
                ApiResponse.success(Success.CONNECT_SUCCESS, socialService.connect(user.getId(), code)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("disconnect")
    public ResponseEntity<ApiResponse> disconnect(@AuthenticationPrincipal User user, @RequestBody final DisconnectRequest request) {

        socialService.disconnect(user.getId(), request.getSocialType());
        return new ResponseEntity<>(
                ApiResponse.success(Success.DISCONNECT_SUCCESS),
                HttpStatus.OK
        );
    }

    @PutMapping("/refresh")
    public ResponseEntity<ApiResponse<OAuth2Token>> refresh(@AuthenticationPrincipal User user, @RequestBody final RefreshRequest request) {

        SocialService socialService = socialServiceProvider.getSocialService(request.getSocialType());
        return new ResponseEntity<>(
                ApiResponse.success(
                        Success.REFRESH_SUCCESS,
                        socialService.refresh(user.getId(), request.getSocialType())
                ),
                HttpStatus.OK
        );
    }
}
