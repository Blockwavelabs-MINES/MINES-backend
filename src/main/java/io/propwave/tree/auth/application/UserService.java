package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.dto.request.UpdateProfileRequestServer;
import io.propwave.tree.auth.domain.Language;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.exception.model.ConflictException;
import io.propwave.tree.exception.model.NotFoundException;
import io.propwave.tree.external.client.aws.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final S3Service s3Service;

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

    @Transactional
    public void updateProfile(Long id, UpdateProfileRequestServer request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        s3Service.deleteFile(user.getProfile().getProfileImg());
        user.updateProfile(request.getProfileBio(), request.getImage(), request.getProfileName());
    }

    @Transactional
    public void updateLanguage(Long id, Language language) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        user.updateLanguage(language);
    }
}
