package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.util.LinkServiceUtil;
import io.propwave.tree.auth.application.util.UserServiceUtil;
import io.propwave.tree.auth.domain.Link;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.LinkRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.auth.ui.dto.request.LinkRequest;
import io.propwave.tree.auth.ui.dto.response.LinkResponse;
import io.propwave.tree.exception.model.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final UserRepository userRepository;
    private final LinkRepository linkRepository;

    @Transactional
    public void createLink(Long id, LinkRequest request) {

        User user = UserServiceUtil.findUserById(userRepository, id);

        linkRepository.save(Link.newInstance(
                request.getLinkTitle(),
                request.getLinkUrl(),
                user
        ));
    }

    @Transactional
    public List<LinkResponse> getList(String userId) {

        User user = UserServiceUtil.findUserByUserId(userRepository, userId);
        List<Link> linkList = linkRepository.findAllByUserId(user.getId());

        return linkList.stream()
                .map(link -> LinkResponse.of(link.getId(), link.getLinkTitle(), link.getLinkUrl())).toList();
    }

    @Transactional
    public void deleteLink(Long id, Long linkId) {

        User user = UserServiceUtil.findUserById(userRepository, id);
        Link link = LinkServiceUtil.findLinkById(linkRepository, linkId);

        if (!link.isLinkUser(user)) {
            throw new ForbiddenException("해당 링크에 권한이 없습니다.");
        }

        linkRepository.delete(link);
    }

    @Transactional
    public void updateLink(Long id, Long linkId, LinkRequest request) {

        User user = UserServiceUtil.findUserById(userRepository, id);
        Link link = LinkServiceUtil.findLinkById(linkRepository, linkId);

        if (!link.isLinkUser(user)) {
            throw new ForbiddenException("해당 링크에 권한이 없습니다.");
        }

        link.update(request.getLinkTitle(), request.getLinkUrl());
    }
}
