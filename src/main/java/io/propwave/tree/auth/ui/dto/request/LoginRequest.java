package io.propwave.tree.auth.ui.dto.request;

import io.propwave.tree.auth.domain.SocialType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {

    @NotNull
    private SocialType socialType;
}
