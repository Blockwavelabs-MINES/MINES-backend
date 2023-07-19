package io.propwave.tree.auth.application.util;

import io.propwave.tree.auth.domain.Link;
import io.propwave.tree.auth.infrastructure.LinkRepository;
import io.propwave.tree.exception.model.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LinkServiceUtil {

    public static Link findLinkById(LinkRepository linkRepository, Long id) {
        return linkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 링크입니다."));
    }
}
