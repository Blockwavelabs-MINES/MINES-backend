package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.dto.response.ProfileDecorateResponseService;
import io.propwave.tree.auth.domain.ProfileDecorate;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.ProfileDecorateRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.exception.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileDecorateService {

    private final UserRepository userRepository;
    private final ProfileDecorateRepository profileDecorateRepository;

    @Transactional
    public ProfileDecorateResponseService getProfileDecorate(String userId) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

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
}
