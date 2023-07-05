package io.propwave.tree.external.client.dto.twitter;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TwitterOAuth2Token {

    private String accessToken;
    private String refreshToken;

    public static TwitterOAuth2Token of(String accessToken, String refreshToken) {
        return new TwitterOAuth2Token(accessToken, refreshToken);
    }
}
