package io.propwave.tree.auth.infrastructure;

import io.propwave.tree.auth.domain.Link;

import java.util.List;

public interface LinkRepositoryCustom {

    List<Link> findAllByUserId(Long userId);
}
