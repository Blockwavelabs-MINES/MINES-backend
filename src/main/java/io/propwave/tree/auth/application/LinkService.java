package io.propwave.tree.auth.application;

import io.propwave.tree.auth.domain.Link;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.LinkRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.auth.ui.dto.request.LinkRequest;
import io.propwave.tree.auth.ui.dto.response.LinkResponse;
import io.propwave.tree.exception.model.ForbiddenException;
import io.propwave.tree.exception.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public List<LinkResponse> getList(String userId) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        List<Link> linkList = linkRepository.findAllByUserId(user.getId());

        return linkList.stream()
                .map(link -> LinkResponse.of(link.getId(), link.getLinkTitle(), link.getLinkUrl()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteLink(Long id, Long linkId) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 링크입니다."));

        if (!link.getUser().equals(user)) {
            throw new ForbiddenException("해당 링크에 권한이 없습니다.");
        }

        linkRepository.delete(link);
    }

    @Transactional
    public void updateLink(Long id, Long linkId, LinkRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 링크입니다."));

        if (!link.getUser().equals(user)) {
            throw new ForbiddenException("해당 링크에 권한이 없습니다.");
        }

        link.update(request.getLinkTitle(), request.getLinkUrl());
    }
}
