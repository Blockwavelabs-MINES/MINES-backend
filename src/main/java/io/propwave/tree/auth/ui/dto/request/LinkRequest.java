package io.propwave.tree.auth.ui.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LinkRequest {

    @NotBlank
    private String linkTitle;

    @NotBlank
    private String linkUrl;
}
