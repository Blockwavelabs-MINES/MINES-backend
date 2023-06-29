package io.propwave.tree.auth.application.dto.response;

import io.propwave.tree.auth.domain.User;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponseService {

    private User user;
    private Boolean isSignup;

    public static LoginResponseService of(User user, Boolean isSignup) {
        return new LoginResponseService(user, isSignup);
    }
}
