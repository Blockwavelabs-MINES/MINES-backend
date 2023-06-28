package io.propwave.tree.auth.ui.dto.response;

import io.propwave.tree.config.security.model.JwtToken;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {

    private JwtToken token;
    private Boolean isSignup;

    public static LoginResponse of(JwtToken token, Boolean status) {
        return new LoginResponse(token, status);
    }
}
