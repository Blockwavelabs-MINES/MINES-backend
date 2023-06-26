package io.propwave.tree.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialInformation {

    @Column(nullable = false, unique = true)
    private String socialId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "social_platform")
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    protected static SocialInformation of (String socialId, String email, SocialType socialType) {
        return new SocialInformation(socialId, email, socialType);
    }
}
