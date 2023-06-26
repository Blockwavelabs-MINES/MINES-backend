package io.propwave.tree.auth.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SocialType {
    GOOGLE("구글"),
    TWITTER("트위터"),
    ;

    private final String value;

    public static SocialType getInstance(String key) {
        if (TWITTER.value.equals(key)) {
            return SocialType.TWITTER;
        }

        return SocialType.GOOGLE;
    }
}
