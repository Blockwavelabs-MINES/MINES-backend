package io.propwave.tree.auth.ui;

import io.propwave.tree.auth.application.LinkService;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.ui.dto.request.LinkRequest;
import io.propwave.tree.auth.ui.dto.response.LinkResponse;
import io.propwave.tree.common.dto.ApiResponse;
import io.propwave.tree.exception.Success;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @PostMapping("/link/add")
    public ResponseEntity<ApiResponse> create(@AuthenticationPrincipal User user, @RequestBody @Valid final LinkRequest request) {
        linkService.createLink(user.getId(), request);
        return new ResponseEntity<>(
                ApiResponse.success(Success.CREATE_LINK_SUCCESS),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/public/link")
    public ResponseEntity<ApiResponse<List<LinkResponse>>> getList(@RequestParam("user_id") String userId) {
        return new ResponseEntity<>(
                ApiResponse.success(
                        Success.GET_LINK_LIST_SUCCESS,
                        linkService.getList(userId)
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/link/{linkId}")
    public ResponseEntity<ApiResponse> delete(@AuthenticationPrincipal User user, @PathVariable String linkId) {
        linkService.deleteLink(user.getId(), Long.parseLong(linkId));
        return new ResponseEntity<>(
                ApiResponse.success(Success.DELETE_LINK_SUCCESS),
                HttpStatus.OK
        );
    }

    @PutMapping("/link/edit/{linkId}")
    public ResponseEntity<ApiResponse> update(
            @AuthenticationPrincipal User user,
            @PathVariable String linkId,
            @RequestBody @Valid final LinkRequest request
    ) {
        linkService.updateLink(user.getId(), Long.parseLong(linkId), request);
        return new ResponseEntity<>(
                ApiResponse.success(Success.UPDATE_LINK_SUCCESS),
                HttpStatus.OK
        );
    }
}
