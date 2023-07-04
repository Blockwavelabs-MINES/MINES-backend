package io.propwave.tree.auth.ui;

import io.propwave.tree.auth.application.ProfileDecorateService;
import io.propwave.tree.auth.application.dto.response.ProfileDecorateResponseService;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.ui.dto.request.ProfileDecorateRequest;
import io.propwave.tree.common.dto.ApiResponse;
import io.propwave.tree.exception.Success;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileDecorateController {

    private final ProfileDecorateService profileDecorateService;

    @GetMapping("/user/profile/decorate")
    public ResponseEntity<ApiResponse<ProfileDecorateResponseService>> getProfileDecorate(@RequestParam("user_id") final String userId) {
        return new ResponseEntity<>(
                ApiResponse.success(
                        Success.GET_PROFILE_DECORATE_SUCCESS,
                        profileDecorateService.getProfileDecorate(userId)
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/user/profile/decorate/edit")
    public ResponseEntity<ApiResponse> updateProfileDecorate(@AuthenticationPrincipal User user, @ModelAttribute @Valid final ProfileDecorateRequest request) {
        profileDecorateService.updateProfileDecorate(user.getId(), request);
        return new ResponseEntity<>(
                ApiResponse.success(Success.UPDATE_PROFILE_DECORATE_SUCCESS),
                HttpStatus.OK
        );
    }
}
