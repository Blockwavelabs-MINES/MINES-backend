package io.propwave.tree.auth.application.dto.request;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateProfileRequestServer {

    private String image;
    private String profileName;
    private String profileBio;

    public static UpdateProfileRequestServer of(String image, String profileName, String profileBio) {
        return new UpdateProfileRequestServer(image, profileName, profileBio);
    }
}
