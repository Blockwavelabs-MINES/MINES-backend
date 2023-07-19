package io.propwave.tree.auth.application;

import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.exception.model.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceUtil {

    public static User findUserUserByEmail(UserRepository userRepository, String email) {
        return userRepository.findBySocialInformationEmail(email)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
    }
}
