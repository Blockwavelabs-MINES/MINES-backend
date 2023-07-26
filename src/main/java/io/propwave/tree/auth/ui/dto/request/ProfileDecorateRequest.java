package io.propwave.tree.auth.ui.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileDecorateRequest {

    @Nullable
    private MultipartFile image;

    @Nullable
    private String backgroundColor;

    @NotBlank
    private String buttonColor;

    @NotBlank
    private String buttonFontColor;

    @NotBlank
    private String fontColor;
}
