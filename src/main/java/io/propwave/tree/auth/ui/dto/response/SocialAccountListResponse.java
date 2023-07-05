package io.propwave.tree.auth.ui.dto.response;

import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.config.security.model.JwtToken;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialAccountListResponse {

    private SocialType socialType;
    private String socialId;

    public static SocialAccountListResponse of(SocialType socialType, String socialId) {
        return new SocialAccountListResponse(socialType, socialId);
    }
}
