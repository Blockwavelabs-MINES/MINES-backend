package io.propwave.tree.external.client.dto.twitter;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TwitterUser {

    private String id;
    private String username;
    private String accessToken;
    private String refreshToken;

    public static TwitterUser of(String id, String username, String accessToken, String refreshToken) {
        return new TwitterUser(id, username, accessToken, refreshToken);
    }
}
