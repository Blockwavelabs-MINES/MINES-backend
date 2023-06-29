package io.propwave.tree.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Profile {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String profileBio;

    @Column(nullable = false, length = 2083)
    private String profileImg;

    @Column(nullable = false)
    private String profileName;

    protected static Profile of(String profileBio, String profileImg, String profileName) {
        return new Profile(profileBio, profileImg, profileName);
    }

    protected void updateProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
