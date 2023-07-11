package io.propwave.tree.auth.ui.dto.response;

import io.propwave.tree.auth.domain.Language;
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
    private Language language;

    public static UserResponse of(Long id, String profileName, String profileImg, String profileBio, String userId, String socialId, Language language) {
        return new UserResponse(id, profileName, profileImg, profileBio, userId, socialId, language);
    }
}
