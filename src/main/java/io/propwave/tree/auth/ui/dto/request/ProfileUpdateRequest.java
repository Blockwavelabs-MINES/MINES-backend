package io.propwave.tree.auth.ui.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileUpdateRequest {

    private MultipartFile image;
    @NotNull
    private String profileName;
    @NotNull
    private String profileBio;
}
