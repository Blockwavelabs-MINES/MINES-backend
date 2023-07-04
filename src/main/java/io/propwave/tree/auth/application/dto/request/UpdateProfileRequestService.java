package io.propwave.tree.auth.application.dto.request;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateProfileRequestService {

    private String image;
    private String profileName;
    private String profileBio;

    public static UpdateProfileRequestService of(String image, String profileName, String profileBio) {
        return new UpdateProfileRequestService(image, profileName, profileBio);
    }
}
