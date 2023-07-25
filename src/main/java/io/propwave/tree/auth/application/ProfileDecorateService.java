package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.dto.response.ProfileDecorateResponseService;
import io.propwave.tree.auth.application.util.ProfileDecorateServiceUtil;
import io.propwave.tree.auth.application.util.UserServiceUtil;
import io.propwave.tree.auth.domain.BackgroundType;
import io.propwave.tree.auth.domain.ProfileDecorate;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.ProfileDecorateRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.auth.ui.dto.request.ProfileDecorateRequest;
import io.propwave.tree.exception.model.BadRequestException;
import io.propwave.tree.exception.model.NotFoundException;
import io.propwave.tree.external.client.aws.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProfileDecorateService {

    private final UserRepository userRepository;
    private final ProfileDecorateRepository profileDecorateRepository;

    private final S3Service s3Service;

    @Transactional
    public ProfileDecorateResponseService getProfileDecorate(String userId) {

        ProfileDecorate profileDecorate = profileDecorateRepository.findByUserUserId(userId)
                .orElseThrow(() -> new NotFoundException("해당 유저의 프로필 꾸미기가 존재하지 않습니다."));

        return ProfileDecorateResponseService.of(
                profileDecorate.getBackgroundType(),
                profileDecorate.getBackgroundColor(),
                profileDecorate.getBackgroundImg(),
                profileDecorate.getButtonColor(),
                profileDecorate.getButtonFontColor(),
                profileDecorate.getFontColor(),
                profileDecorate.getUser().getUserId()
        );
    }

    @Transactional
    public void updateProfileDecorate(Long id, ProfileDecorateRequest request) {
        String imageUrl = null;

        User user = UserServiceUtil.findUserById(userRepository, id);
        ProfileDecorate profileDecorate = ProfileDecorateServiceUtil.findProfileDecorateByUserId(profileDecorateRepository, user.getUserId());
        BackgroundType backgroundType = checkBackgroundType(request.getBackgroundColor(), request.getImage());

        if (backgroundType == BackgroundType.IMAGE) {
            imageUrl = s3Service.uploadImage(request.getImage(), "profile/decorate");
        }

        if (profileDecorate.getBackgroundType() == BackgroundType.IMAGE) {
            s3Service.deleteFile(profileDecorate.getBackgroundImg());
        }

        profileDecorate.update(
                backgroundType,
                request.getBackgroundColor(),
                imageUrl,
                request.getButtonColor(),
                request.getButtonFontColor(),
                request.getFontColor()
        );
    }

    private BackgroundType checkBackgroundType(String backgroundColor, MultipartFile backgroundImg) {
        if (backgroundColor.length() == 0 && backgroundImg.isEmpty()) {
            throw new BadRequestException("배경 색과 배경 이미지 모두 요청되지 않았습니다.");
        }

        if (backgroundColor.length() != 0 && !backgroundImg.isEmpty()) {
            throw new BadRequestException("배경 색과 배경 이미지 모두 요청됐습니다.");
        }

        if (!backgroundImg.isEmpty()) {
            return BackgroundType.IMAGE;
        }

        return BackgroundType.COLOR;
    }
}
