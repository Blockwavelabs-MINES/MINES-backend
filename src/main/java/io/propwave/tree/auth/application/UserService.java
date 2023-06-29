package io.propwave.tree.auth.application;

import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.exception.model.ConflictException;
import io.propwave.tree.exception.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void updateUserId(Long id, String userId) {

        Boolean isDuplicated = userRepository.existsByUserId(userId);
        if (isDuplicated) {
            throw new ConflictException("이미 사용중인 유저 아이디입니다.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        user.updateUserId(userId);
    }
}
