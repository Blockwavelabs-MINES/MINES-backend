package io.propwave.tree.auth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialUser {

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

    @Column(nullable = false)
    private Boolean isConnected;

    @Builder
    private SocialUser(User user, String socialId, SocialType socialType, String username, String accessToken, String refreshToken, Boolean isConnected) {
        this.user = user;
        this.socialId = socialId;
        this.socialType = socialType;
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isConnected = isConnected;
    }

    public static SocialUser newInstance(User user, String socialId, SocialType socialType, String username, String accessToken, String refreshToken, Boolean isConnected) {
        return new SocialUser(user, socialId, socialType, username, accessToken, refreshToken, isConnected);
    }
}
