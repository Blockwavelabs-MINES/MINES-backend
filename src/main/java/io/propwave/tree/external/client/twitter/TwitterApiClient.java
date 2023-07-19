package io.propwave.tree.external.client.twitter;

import io.propwave.tree.external.client.dto.OAuth2Token;
import io.propwave.tree.external.client.dto.twitter.TweetInfo;
import io.propwave.tree.external.client.dto.twitter.TweetText;
import io.propwave.tree.external.client.dto.twitter.TwitterOAuth2Token;
import io.propwave.tree.external.client.dto.twitter.TwitterUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "twitterApiClient", url = "https://api.twitter.com")
public interface TwitterApiClient {

    @PostMapping(value = "/2/oauth2/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TwitterOAuth2Token getAccessToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken,
            @RequestParam("code") String code,
            @RequestParam("grant_type") String grantType,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code_verifier") String codeVerifier
    );

    @GetMapping(value = "/2/users/me")
    TwitterUserInfo getUserProfile(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken
    );

    @PostMapping(value = "/2/tweets", consumes = MediaType.APPLICATION_JSON_VALUE)
    TweetInfo createTweet(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken,
            @RequestBody TweetText text
    );

    @PostMapping(value = "/2/oauth2/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    OAuth2Token getAccessTokenByRefreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken,
            @RequestParam("grant_type") String grantType,
            @RequestParam("refresh_token") String refreshToken
    );
}
