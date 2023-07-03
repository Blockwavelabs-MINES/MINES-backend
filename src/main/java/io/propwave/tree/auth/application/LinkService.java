package io.propwave.tree.auth.application;

import io.propwave.tree.auth.domain.Link;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.LinkRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.auth.ui.dto.request.LinkRequest;
import io.propwave.tree.exception.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final UserRepository userRepository;
    private final LinkRepository linkRepository;

    @Transactional
    public void createLink(Long id, LinkRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        linkRepository.save(Link.newInstance(
                request.getLinkTitle(),
                request.getLinkUrl(),
                user
        ));
    }
}
