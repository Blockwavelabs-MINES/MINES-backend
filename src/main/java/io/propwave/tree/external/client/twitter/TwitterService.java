package io.propwave.tree.external.client.twitter;

import io.propwave.tree.auth.domain.SocialUser;
import io.propwave.tree.auth.infrastructure.SocialUserRepository;
import io.propwave.tree.exception.model.NotFoundException;
import io.propwave.tree.external.client.dto.OAuth2Token;
import io.propwave.tree.external.client.dto.twitter.*;
import io.propwave.tree.utils.SamTreeUtil;
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

    private final SocialUserRepository socialUserRepository;

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

    public TweetLink createTweet(Long userId, TweetRequest request) {

        SocialUser socialUser = socialUserRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        String content = request.getTweetType().equals(TweetType.SENDER) ?
                SamTreeUtil.makeSendTweetContent(socialUser.getUser().getLanguage(), request.getComment(), request.getTokenTicker(), request.getTokenAmount(), request.getTime(), request.getReceiverUsername()) :
                SamTreeUtil.makeReceiveTweetContent(socialUser.getUser().getLanguage(), request.getTokenTicker(), request.getTokenAmount(), request.getTime(), request.getSenderUsername());

        TweetInfo tweetInfo = twitterApiClient.createTweet(
                "Bearer " + socialUser.getAccessToken(),
                TweetText.of(content)
        );

        return TweetLink.of("https://twitter.com/" + socialUser.getUsername() + "/status" + tweetInfo.getData().getId());
    }

    public OAuth2Token getTokenByRefreshToken(String refreshToken) {
        return twitterApiClient.getAccessTokenByRefreshToken(
                makeBearerToken(),
                "refresh_token",
                refreshToken
        );
    }

    private String makeBearerToken() {
        String bearerToken = clientId + ":" + clientSecret;
        return "Basic " + Base64.getEncoder().encodeToString(bearerToken.getBytes());
    }
}
