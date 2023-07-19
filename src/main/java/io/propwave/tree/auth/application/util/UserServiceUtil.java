package io.propwave.tree.auth.application.util;

import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.exception.model.ConflictException;
import io.propwave.tree.exception.model.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceUtil {

    public static User findUserUserByEmail(UserRepository userRepository, String email) {
        return userRepository.findBySocialInformationEmail(email)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
    }

    public static User findUserById(UserRepository userRepository, Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
    }

    public static User findUserByUserId(UserRepository userRepository, String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
    }

    public static void validNotExistsUserId(UserRepository userRepository, String userId) {
        if(userRepository.existsByUserId(userId)) {
            throw new ConflictException("이미 사용중인 유저 아이디입니다.");
        }
    }
}
