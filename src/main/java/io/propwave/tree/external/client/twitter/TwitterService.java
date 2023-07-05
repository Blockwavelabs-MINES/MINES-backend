package io.propwave.tree.external.client.twitter;

import io.propwave.tree.external.client.dto.twitter.TwitterOAuth2Token;
import io.propwave.tree.external.client.dto.twitter.TwitterUser;
import io.propwave.tree.external.client.dto.twitter.TwitterUserData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class TwitterService {

    @Value("${twitter.clientId}")
    private String clientId;

    @Value("${twitter.clientSecret}")
    private String clientSecret;

    @Value("${twitter.redirectUri}")
    private String redirectUri;

    @Value("${twitter.codeVerifier}")
    private String codeVerifier;

    private final TwitterApiClient twitterApiClient;

    public TwitterUser getUserInfo(String code) {

        TwitterOAuth2Token twitterOAuth2Token = twitterApiClient.getAccessToken(
                makeBearerToken(),
                code,
                "authorization_code",
                redirectUri,
                codeVerifier
        );

        TwitterUserData twitterUserData = twitterApiClient.getUserProfile("Bearer " + twitterOAuth2Token.getAccessToken()).getData();
        return TwitterUser.of(
                twitterUserData.getId(),
                twitterUserData.getUsername(),
                twitterOAuth2Token.getAccessToken(),
                twitterOAuth2Token.getRefreshToken()
        );
    }

    private String makeBearerToken() {
        String bearerToken = clientId + ":" + clientSecret;
        return "Basic " + Base64.getEncoder().encodeToString(bearerToken.getBytes());
    }
}
