package io.propwave.tree.auth.infrastructure;

import io.propwave.tree.auth.domain.Link;
import org.springframework.data.repository.Repository;

public interface LinkRepository extends Repository<Link, Long> {

    // CREATE
    void save(Link link);
}
