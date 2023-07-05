package io.propwave.tree.external.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OAuth2Token {

    private String accessToken;
    private String refreshToken;

    public static OAuth2Token of(String accessToken, String refreshToken) {
        return new OAuth2Token(accessToken, refreshToken);
    }
}
