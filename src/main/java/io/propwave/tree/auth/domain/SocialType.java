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
}
