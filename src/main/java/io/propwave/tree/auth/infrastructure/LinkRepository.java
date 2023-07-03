package io.propwave.tree.auth.infrastructure;

import io.propwave.tree.auth.domain.Link;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface LinkRepository extends Repository<Link, Long>, LinkRepositoryCustom {

    // CREATE
    void save(Link link);

    // READ
    Optional<Link> findById(Long id);

    // UPDATE

    // DELETE
    void delete(Link link);
}
