package io.propwave.tree.auth.domain;

import io.propwave.tree.common.domain.AuditingTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialUser extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    @Builder
    private SocialUser(User user, String socialId, SocialType socialType, String username, String accessToken, String refreshToken) {
        this.user = user;
        this.socialId = socialId;
        this.socialType = socialType;
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static SocialUser newInstance(User user, String socialId, SocialType socialType, String username, String accessToken, String refreshToken, Boolean isConnected) {
        return new SocialUser(user, socialId, socialType, username, accessToken, refreshToken);
    }

    public void refreshOAuth2Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
