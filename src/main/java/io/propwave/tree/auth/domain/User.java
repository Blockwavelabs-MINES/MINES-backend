package io.propwave.tree.auth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Embedded
    private Profile profile;

    @Embedded
    private SocialInformation socialInformation;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ENG'")
    private Language language;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private User(String userId, String profileBio, String profileImg, String profileName, String socialId, String email, SocialType socialType, Role role) {
        this.userId = userId;
        this.profile = Profile.of(profileBio, profileImg, profileName);
        this.socialInformation = SocialInformation.of(socialId, email, socialType);
        this.role = role;
    }

    public static User newInstance(String userId, String profileBio, String profileImg, String profileName, String socialId, String email, SocialType socialType, Role role) {
        return User.builder()
                .userId(userId)
                .profileBio(profileBio)
                .profileImg(profileImg)
                .profileName(profileName)
                .socialId(socialId)
                .email(email)
                .socialType(socialType)
                .role(role)
                .build();
    }

    public User update(String email, String profileImg) {
        this.socialInformation.updateEmail(email);
        this.profile.updateProfileImg(profileImg);

        return this;
    }
}