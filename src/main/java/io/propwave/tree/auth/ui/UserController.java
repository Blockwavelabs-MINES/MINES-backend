package io.propwave.tree.auth.ui;

import io.propwave.tree.auth.application.UserService;
import io.propwave.tree.auth.application.dto.request.UpdateProfileRequestServer;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.auth.ui.dto.request.ProfileUpdateRequest;
import io.propwave.tree.auth.ui.dto.response.CheckUserIdResponse;
import io.propwave.tree.common.dto.ApiResponse;
import io.propwave.tree.exception.Success;
import io.propwave.tree.external.client.aws.S3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final S3Service s3Service;

    private final UserRepository userRepository;

    @GetMapping("/id/check")
    public ResponseEntity<ApiResponse<CheckUserIdResponse>> checkUserId(@RequestParam("user_id") final String userId) {
        return new ResponseEntity<>(
                ApiResponse.success(
                        Success.CHECK_USER_ID_SUCCESS,
                        CheckUserIdResponse.of(userRepository.existsByUserId(userId))
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/id/edit")
    public ResponseEntity<ApiResponse> updateUserId(@AuthenticationPrincipal User user, @RequestParam("user_id") final String userId) {
        userService.updateUserId(user.getId(), userId);
        return new ResponseEntity<>(ApiResponse.success(Success.UPDATE_USER_ID_SUCCESS), HttpStatus.OK);
    }

    @PutMapping(value = "/profile/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updateProfile(@AuthenticationPrincipal User user, @ModelAttribute @Valid final ProfileUpdateRequest request) {
        String imageUrl = s3Service.uploadImage(request.getImage(), "profile");
        userService.updateProfile(user.getId(), UpdateProfileRequestServer.of(imageUrl, request.getProfileName(), request.getProfileBio()));
        return new ResponseEntity<>(ApiResponse.success(Success.UPDATE_PROFILE_SUCCESS), HttpStatus.OK);
    }
}
