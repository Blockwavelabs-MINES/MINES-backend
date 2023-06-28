package io.propwave.tree.auth.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "token", timeToLive = 1209600)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {

    @Id
    private String refreshToken;

    private String email;

    public static RefreshToken newInstance(String refreshToken, String email) {
        return new RefreshToken(refreshToken, email);
    }
}
