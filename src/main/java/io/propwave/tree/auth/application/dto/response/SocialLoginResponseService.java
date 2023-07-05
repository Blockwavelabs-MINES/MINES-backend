package io.propwave.tree.auth.application.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialLoginResponseService {

    private String userId;

    public static SocialLoginResponseService of(String userId) {
        return new SocialLoginResponseService(userId);
    }
}
