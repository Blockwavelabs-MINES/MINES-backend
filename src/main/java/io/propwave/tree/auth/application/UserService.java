package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.dto.request.UpdateProfileRequestService;
import io.propwave.tree.auth.application.util.UserServiceUtil;
import io.propwave.tree.auth.domain.Language;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.exception.model.ConflictException;
import io.propwave.tree.exception.model.NotFoundException;
import io.propwave.tree.external.client.aws.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final S3Service s3Service;

    private final UserRepository userRepository;

    @Transactional
    public void updateUserId(Long id, String userId) {

        UserServiceUtil.validNotExistsUserId(userRepository, userId);
        User user = UserServiceUtil.findUserById(userRepository, id);
        user.updateUserId(userId);
    }

    @Transactional
    public void updateProfile(Long id, MultipartFile image, UpdateProfileRequestService request) {
        String imageUrl;

        User user = UserServiceUtil.findUserById(userRepository, id);

        // 프로필 이미지가 변경되는 경우
        if (image != null) {
            imageUrl = s3Service.uploadImage(image, "profile");
            s3Service.deleteFile(user.getProfile().getProfileImg());

            user.updateProfileWithImage(request.getProfileBio(), imageUrl, request.getProfileName());
        }

        user.updateProfile(request.getProfileBio(), request.getProfileName());
    }

    @Transactional
    public void updateLanguage(Long id, Language language) {

        User user = UserServiceUtil.findUserById(userRepository, id);

        user.updateLanguage(language);
    }
}
