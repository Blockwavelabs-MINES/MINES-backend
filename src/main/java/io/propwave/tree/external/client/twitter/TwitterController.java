package io.propwave.tree.external.client.twitter;

import io.propwave.tree.auth.domain.User;
import io.propwave.tree.common.dto.ApiResponse;
import io.propwave.tree.exception.Success;
import io.propwave.tree.external.client.dto.twitter.TweetLink;
import io.propwave.tree.external.client.dto.twitter.TweetRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TwitterController {

    private final TwitterService twitterService;

    @PostMapping("/tweet")
    public ResponseEntity<ApiResponse<TweetLink>> createTweet(@AuthenticationPrincipal User user, @RequestBody @Valid final TweetRequest request) {
        return new ResponseEntity<>(
                ApiResponse.success(Success.TWEET_SUCCESS, twitterService.createTweet(user.getId(), request)),
                HttpStatus.CREATED
        );
    }
}
