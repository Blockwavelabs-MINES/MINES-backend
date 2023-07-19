package io.propwave.tree.auth.application.util;

import io.propwave.tree.auth.domain.ProfileDecorate;
import io.propwave.tree.auth.infrastructure.ProfileDecorateRepository;
import io.propwave.tree.exception.model.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileDecorateServiceUtil {

    public static ProfileDecorate findProfileDecorateByUserId(ProfileDecorateRepository profileDecorateRepository, String userId) {
        return profileDecorateRepository.findByUserUserId(userId)
                .orElseThrow(() -> new NotFoundException("해당 유저의 프로필 꾸미기가 존재하지 않습니다."));
    }
}
