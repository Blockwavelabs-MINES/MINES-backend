package io.propwave.tree.auth.ui.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileDecorateRequest {

    private MultipartFile image;

    private String backgroundColor;

    @NotBlank
    private String buttonColor;

    @NotBlank
    private String buttonFontColor;

    @NotBlank
    private String fontColor;
}
