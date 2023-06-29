package io.propwave.tree.auth.ui.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

    private Long id;
    private String profileName;
    private String profileImg;
    private String profileBio;
    private String userId;
    private String socialId;

    public static UserResponse of(Long id, String profileName, String profileImg, String profileBio, String userId, String socialId) {
        return new UserResponse(id, profileName, profileImg, profileBio, userId, socialId);
    }
}
